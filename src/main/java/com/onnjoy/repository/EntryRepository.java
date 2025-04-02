package com.onnjoy.repository;

import com.onnjoy.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query("SELECT COUNT(e) FROM Entry e WHERE e.userId = :userId AND e.createdAt >= :weekAgoDate")
    int countEntriesThisWeek(@Param("userId") Long userId, @Param("weekAgoDate") LocalDateTime weekAgoDate);

    Optional<Entry> findTopByUserIdOrderByCreatedAtDesc(Long userId);  //  Optional for null safety
}
