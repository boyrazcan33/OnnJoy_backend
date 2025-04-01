package com.onnjoy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentRequest {
    private Long userId;
    private Long therapistId;
    private LocalDate date;
    private String packageType;
}
