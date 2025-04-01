package com.onnjoy.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}