package com.onnjoy.controller;

import com.onnjoy.model.User;
import com.onnjoy.repository.UserRepository;
import com.onnjoy.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public Map<String, Object> getMe(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
                "email", user.getEmail(),
                "anonUsername", user.getAnonUsername(),
                "language", user.getLanguagePreference(),
                "token", token
        );
    }
}
