package com.onnjoy.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AppointmentRequest {
    private Long userId;
    private Long therapistId;
    private String packageType;
    private LocalDate date; // for single appointment booking
    private List<LocalDate> selectedDates; // for package-based bookings (multiple days)
}
