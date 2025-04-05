package com.onnjoy.service;

import com.onnjoy.model.Therapist;
import com.onnjoy.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TherapistBioService {

    private final TherapistRepository therapistRepository;

    public Optional<Therapist> getTherapistById(Long id) {
        return therapistRepository.findById(id);
    }

    public List<Therapist> getTherapistsByLanguage(String language) {
        return therapistRepository.findByLanguage(language);
    }
}