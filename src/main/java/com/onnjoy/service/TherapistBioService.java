package com.onnjoy.service;

import com.onnjoy.model.Therapist;
import com.onnjoy.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TherapistBioService {

    private final TherapistRepository therapistRepository;
    private final JdbcTemplate jdbc;

    public Optional<Map<String, Object>> getTherapistById(Long id) {
        // First get basic therapist info
        Optional<Therapist> basicTherapist = therapistRepository.findById(id);

        if (basicTherapist.isEmpty()) {
            return Optional.empty();
        }

        // Then get the bio from therapist_bios table
        List<Map<String, Object>> results = jdbc.queryForList("""
            SELECT t.id, t.name as full_name, tb.bio, tb.language_code
            FROM therapist_bios tb
            JOIN therapists t ON tb.therapist_id = t.id
            WHERE t.id = ?
        """, id);

        if (results.isEmpty()) {
            // Return at least the basic info even without bio
            Map<String, Object> therapistData = new HashMap<>();
            therapistData.put("id", basicTherapist.get().getId());
            therapistData.put("full_name", basicTherapist.get().getName());
            therapistData.put("bio", "Bio not available");
            therapistData.put("language", basicTherapist.get().getLanguage());
            return Optional.of(therapistData);
        }

        return Optional.of(results.get(0));
    }

    public List<Therapist> getTherapistsByLanguage(String language) {
        return therapistRepository.findByLanguage(language);
    }
}