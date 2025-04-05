package com.onnjoy.controller;

import com.onnjoy.dto.UserSignupRequest;
import com.onnjoy.dto.UserLoginRequest;
import com.onnjoy.dto.AuthResponse;
import com.onnjoy.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody UserSignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequest request) {
        AuthResponse response = authService.login(request);
        System.out.println("Token generated for user " + request.getEmail() + ": " +
                (response.getToken().length() > 15 ? response.getToken().substring(0, 15) + "..." : response.getToken()));
        return ResponseEntity.ok(authService.login(request));
    }
}
