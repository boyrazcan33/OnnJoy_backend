package com.onnjoy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapistAvailabilityService {

    private final JdbcTemplate jdbc;

    public void saveAvailability(Long therapistId, List<LocalDate> dates) {
        // Clear old availability (optional)
        jdbc.update("DELETE FROM therapist_availability WHERE therapist_id = ?", therapistId);

        for (LocalDate date : dates) {
            jdbc.update("""
                INSERT INTO therapist_availability (therapist_id, available_date, created_at)
                VALUES (?, ?, now())
            """, therapistId, date);
        }
    }

    public List<LocalDate> getAvailability(Long therapistId) {
        return jdbc.queryForList("""
            SELECT available_date
            FROM therapist_availability
            WHERE therapist_id = ?
            ORDER BY available_date
        """, LocalDate.class, therapistId);
    }
}
