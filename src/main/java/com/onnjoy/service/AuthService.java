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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(UserSignupRequest request) {
        String anonUsername = generateAnonUsername();

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setAnonUsername(anonUsername);
        user.setLanguagePreference(request.getLanguagePreference());
        user.setCreatedAt(Timestamp.from(Instant.now()));

        String token = jwtUtil.generateToken(user.getEmail());
        user.setJwtToken(token);

        userRepository.save(user);

        return new AuthResponse(token, anonUsername);
    }

    public AuthResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(" Email: " + request.getEmail());
        System.out.println(" Raw password from Postman: " + request.getPassword());
        System.out.println(" Hashed password from DB: " + user.getPasswordHash());
        System.out.println(" Match result: " + passwordEncoder.matches(request.getPassword(), user.getPasswordHash()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        user.setJwtToken(token);
        userRepository.save(user);

        return new AuthResponse(token, user.getAnonUsername());
    }

    private String generateAnonUsername() {
        String[] colors = {
                "Blue", "Red", "Green", "Violet", "Amber", "Indigo", "Sapphire", "Gold", "Silver", "Rose"
        };

        String[] beautifulAnimals = {
                "Swan", "Panther", "Fox", "Owl", "Deer", "Dolphin", "Peacock", "Butterfly", "Tiger", "Wolf"
        };

        Random random = new Random();
        String color = colors[random.nextInt(colors.length)];
        String animal = beautifulAnimals[random.nextInt(beautifulAnimals.length)];
        int number = 100 + random.nextInt(900); // 100–999

        return color + animal + number;
    }

}
