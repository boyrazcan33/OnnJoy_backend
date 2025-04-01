package com.onnjoy.controller;

import com.onnjoy.dto.AppointmentRequest;
import com.onnjoy.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;


import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppointmentController {

    private final JdbcTemplate jdbc;

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
}
