package com.onnjoy.controller;

import com.onnjoy.model.Therapist;
import com.onnjoy.service.TherapistBioService;
import com.onnjoy.service.TherapistBioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/therapists")
@RequiredArgsConstructor
public class TherapistBioController {

    private final TherapistBioService therapistService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTherapistDetails(@PathVariable Long id) {
        Optional<Therapist> therapist = therapistService.getTherapistById(id);

        if (therapist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(therapist.get());
    }
}