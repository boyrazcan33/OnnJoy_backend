package com.onnjoy.controller;

import com.onnjoy.service.TherapistAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class TherapistAvailabilityController {

    private final TherapistAvailabilityService availabilityService;

    @PostMapping("/{therapistId}")
    public void addAvailability(
            @PathVariable Long therapistId,
            @RequestBody List<LocalDate> availableDates
    ) {
        availabilityService.saveAvailability(therapistId, availableDates);
    }

    @GetMapping("/{therapistId}")
    public List<LocalDate> getAvailability(@PathVariable Long therapistId) {
        return availabilityService.getAvailability(therapistId);
    }
}
