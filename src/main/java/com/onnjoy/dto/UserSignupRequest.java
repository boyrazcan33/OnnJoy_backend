package com.onnjoy.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.Data;

@Data
public class UserSignupRequest {
    private String email;
    private String password;
    private String languagePreference;
}

