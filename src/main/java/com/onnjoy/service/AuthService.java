package com.onnjoy.service;

import com.onnjoy.dto.UserSignupRequest;
import com.onnjoy.dto.UserLoginRequest;
import com.onnjoy.dto.AuthResponse;
import com.onnjoy.model.User;
import com.onnjoy.repository.UserRepository;
import com.onnjoy.config.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(UserSignupRequest request) {
        // Generate unique anonymous username
        String anonUsername = generateAnonUsername();

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setAnonUsername(anonUsername);
        user.setLanguagePreference(request.getLanguagePreference());
        user.setCreatedAt(Timestamp.from(Instant.now()));

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, anonUsername);
    }

    public AuthResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getAnonUsername());
    }

    private String generateAnonUsername() {
        // Placeholder: use your color-animal-number logic here
        return "BlueWolf" + (int)(Math.random() * 900 + 100);
    }
}

