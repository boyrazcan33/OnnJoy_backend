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

        // Use direct JDBC query to get therapist data with correct field names
        List<Map<String, Object>> therapistResults = jdbc.queryForList(
                "SELECT id, full_name, profile_picture_url FROM therapists WHERE id = ?", id);

        if (therapistResults.isEmpty()) {
            System.out.println("No therapist found with ID: " + id);
            return ResponseEntity.notFound().build();
        }

        // Create response with database column names
        Map<String, Object> response = new HashMap<>(therapistResults.get(0));

        // Try to get bio from therapist_bios table
        try {
            List<Map<String, Object>> bioResults = jdbc.queryForList(
                    "SELECT bio, language_code FROM therapist_bios WHERE therapist_id = ? LIMIT 1", id);

            if (!bioResults.isEmpty() && bioResults.get(0).containsKey("bio")) {
                response.put("bio", bioResults.get(0).get("bio"));
                response.put("language", bioResults.get(0).get("language_code"));
                System.out.println("Found bio in therapist_bios table");
            } else {
                // Alternative: get bio from therapists table directly
                List<Map<String, Object>> therapistBio = jdbc.queryForList(
                        "SELECT bio FROM therapists WHERE id = ?", id);
                if (!therapistBio.isEmpty() && therapistBio.get(0).containsKey("bio")) {
                    response.put("bio", therapistBio.get(0).get("bio"));
                }
                System.out.println("Using bio from therapists table");
            }
        } catch (Exception e) {
            System.out.println("Error fetching bio: " + e.getMessage());
        }

        // Add default match score
        response.put("match_score", 0.0);

        System.out.println("Returning response: " + response);
        return ResponseEntity.ok(response);
    }
}