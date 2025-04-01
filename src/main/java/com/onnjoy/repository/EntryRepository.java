package com.onnjoy.repository;

import com.onnjoy.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByUserEmail(String email);
}
