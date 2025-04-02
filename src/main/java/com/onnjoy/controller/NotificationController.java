package com.onnjoy.controller;

import com.onnjoy.config.JwtUtil;
import com.onnjoy.model.User;
import com.onnjoy.repository.UserRepository;
import com.onnjoy.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // GET all notifications for the authenticated user
    @GetMapping
    public ResponseEntity<?> getNotifications(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        return ResponseEntity.ok(notificationService.getUserNotifications(user.getId()));
    }







    // Mark a single notification as read
    @PostMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }

    private String extractToken(String header) {
        return header.replace("Bearer ", "");
    }
}
