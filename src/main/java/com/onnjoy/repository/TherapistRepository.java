package com.onnjoy.repository;

import com.onnjoy.model.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TherapistRepository extends JpaRepository<Therapist, Long> {
    List<Therapist> findByLanguage(String language);
}
