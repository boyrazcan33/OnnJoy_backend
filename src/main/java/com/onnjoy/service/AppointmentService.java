package com.onnjoy.service;

import com.onnjoy.dto.AppointmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final JdbcTemplate jdbc;
    private final NotificationService notificationService;

    public void bookAppointment(AppointmentRequest request) {
        int available = jdbc.queryForObject("""
            SELECT COUNT(*) FROM therapist_availability
            WHERE therapist_id = ? AND available_date = ?
        """, Integer.class, request.getTherapistId(), request.getDate());

        if (available == 0) {
            throw new IllegalArgumentException("Therapist not available on selected date");
        }

        // Insert appointment
        jdbc.update("""
            INSERT INTO appointments 
            (user_id, therapist_id, scheduled_at, status, package_type, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """, request.getUserId(), request.getTherapistId(),
                Timestamp.valueOf(request.getDate().atStartOfDay()),
                "confirmed", request.getPackageType(), Timestamp.valueOf(LocalDateTime.now()));

        // Remove availability after booking
        jdbc.update("""
            DELETE FROM therapist_availability 
            WHERE therapist_id = ? AND available_date = ?
        """, request.getTherapistId(), request.getDate());

        notificationService.sendNotification(
                request.getUserId(),
                "Your appointment has been confirmed ✅",
                "appointment"
        );
    }
}
