package com.onnjoy.controller;

import com.onnjoy.dto.AppointmentRequest;
import com.onnjoy.model.User;
import com.onnjoy.repository.UserRepository;
import com.onnjoy.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppointmentController {

    private final JdbcTemplate jdbc;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @GetMapping("/appointments")
    public List<Map<String, Object>> getAppointments(Principal principal) {
        String email = principal.getName();

        return jdbc.queryForList("""
            SELECT a.id, a.scheduled_at, a.status, a.package_type,
                   a.pre_session_message,
                   t.full_name, t.profile_picture_url
            FROM appointments a
            JOIN users u ON a.user_id = u.id
            JOIN therapists t ON a.therapist_id = t.id
            WHERE u.email = ?
            ORDER BY a.scheduled_at DESC
        """, email);
    }

    @PostMapping("/appointments")
    @Transactional
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request, Principal principal) {
        String email = principal.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        Long userId = userOpt.get().getId();
        Long therapistId = request.getTherapistId();
        String packageType = request.getPackageType();
        List<LocalDate> selectedDates = request.getSelectedDates();

        int expectedCount = switch (packageType.toLowerCase()) {
            case "single" -> 1;
            case "monthly" -> 4;
            case "intensive" -> 8;
            default -> 0;
        };

        if (selectedDates.size() != expectedCount) {
            return ResponseEntity.badRequest().body("Invalid number of selected dates for package: " + packageType);
        }

        for (LocalDate date : selectedDates) {
            jdbc.update("""
                INSERT INTO appointments (user_id, therapist_id, scheduled_at, status, package_type, created_at)
                VALUES (?, ?, ?, 'confirmed', ?, ?)
            """, userId, therapistId, Timestamp.valueOf(date.atStartOfDay()), packageType, Timestamp.valueOf(LocalDateTime.now()));
        }

        //  Send notification after booking
        notificationService.sendNotification(
                userId,
                "Your appointment(s) have been confirmed!",
                "appointment"
        );

        return ResponseEntity.ok("Appointment(s) booked successfully!");
    }
}
