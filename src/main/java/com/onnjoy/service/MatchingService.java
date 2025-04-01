package com.onnjoy.service;

import com.onnjoy.util.VectorUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final JdbcTemplate jdbc;
    private final EmbeddingService embeddingService;

    @Transactional
    public List<Map<String, Object>> matchTherapists(Long entryId) {
        // 1. Get entry
        Map<String, Object> entry = jdbc.queryForMap("SELECT * FROM entries WHERE id = ?", entryId);
        String userText = (String) entry.get("content");
        String lang = (String) entry.get("language_code");

        // 2. Generate user embedding
        List<Double> userEmbedding = embeddingService.getEmbeddingVector(userText);

        // 3. Get therapist bios in same language + join therapist info
        List<Map<String, Object>> bios = jdbc.queryForList("""
            SELECT 
              b.id AS bio_id,
              b.therapist_id,
              b.bio,
              t.full_name,
              t.profile_picture_url
            FROM therapist_bios b
            JOIN therapists t ON b.therapist_id = t.id
            WHERE b.language_code = ?
        """, lang);

        // 4. Score and rank therapists
        List<Map<String, Object>> ranked = new ArrayList<>();
        for (Map<String, Object> bioRow : bios) {
            String bioText = (String) bioRow.get("bio");
            List<Double> bioEmbedding = embeddingService.getEmbeddingVector(bioText);
            double score = VectorUtils.cosineSimilarity(userEmbedding, bioEmbedding);

            bioRow.put("match_score", score);
            ranked.add(bioRow);
        }

        ranked.sort((a, b) -> Double.compare((Double) b.get("match_score"), (Double) a.get("match_score")));

        // 5. Save top 2 matches into DB
        jdbc.update("DELETE FROM matches WHERE entry_id = ?", entryId);

        for (int i = 0; i < Math.min(2, ranked.size()); i++) {
            Map<String, Object> match = ranked.get(i);
            jdbc.update("""
                INSERT INTO matches (entry_id, therapist_id, match_score, rank, created_at)
                VALUES (?, ?, ?, ?, ?)
            """, entryId, match.get("therapist_id"), match.get("match_score"), i + 1, LocalDateTime.now());
        }

        return ranked.subList(0, Math.min(2, ranked.size()));
    }
}
