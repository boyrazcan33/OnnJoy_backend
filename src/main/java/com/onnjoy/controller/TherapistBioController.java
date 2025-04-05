package com.onnjoy.controller;

import com.onnjoy.model.Therapist;
import com.onnjoy.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/therapists")
@RequiredArgsConstructor
public class TherapistBioController {

    private final TherapistRepository therapistRepository;
    private final JdbcTemplate jdbc;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTherapistDetails(@PathVariable Long id) {
        System.out.println("Received request for therapist with ID: " + id);

        // First try to get from Therapist table
        Optional<Therapist> therapistOpt = therapistRepository.findById(id);

        if (therapistOpt.isEmpty()) {
            System.out.println("No therapist found with ID: " + id);
            return ResponseEntity.notFound().build();
        }

        Therapist therapist = therapistOpt.get();

        // Create a response map with basic therapist details
        Map<String, Object> response = new HashMap<>();
        response.put("id", therapist.getId());
        response.put("full_name", therapist.getName());
        response.put("language", therapist.getLanguage());

        // Try to get bio from therapist_bios table if it exists
        try {
            List<Map<String, Object>> bioResults = jdbc.queryForList(
                    "SELECT bio FROM therapist_bios WHERE therapist_id = ? LIMIT 1", id);

            if (!bioResults.isEmpty() && bioResults.get(0).containsKey("bio")) {
                response.put("bio", bioResults.get(0).get("bio"));
                System.out.println("Found bio in therapist_bios table");
            } else {
                // Alternative: get bio from therapists table directly
                response.put("bio", therapist.getBio());
                System.out.println("Using bio from therapists table");
            }
        } catch (Exception e) {
            System.out.println("Error fetching bio: " + e.getMessage());
            // If there's any error, use the bio from the therapist object
            response.put("bio", therapist.getBio());
        }

        // Add additional fields that might be expected by the frontend
        response.put("match_score", 0.0); // Default match score

        System.out.println("Returning response: " + response);
        return ResponseEntity.ok(response);
    }
}