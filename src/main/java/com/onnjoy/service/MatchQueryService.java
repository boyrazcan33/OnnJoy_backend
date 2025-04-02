package com.onnjoy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchQueryService {

    private final JdbcTemplate jdbc;

    public List<Map<String, Object>> getMatchesForEntry(Long entryId) {
        return jdbc.queryForList("""
            SELECT 
                m.rank,
                m.match_score,
                t.full_name,
                t.profile_picture_url
            FROM matches m
            JOIN therapists t ON m.therapist_id = t.id
            WHERE m.entry_id = ?
            ORDER BY m.rank ASC
        """, entryId);
    }
}
