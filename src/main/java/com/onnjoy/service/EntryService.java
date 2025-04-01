package com.onnjoy.service;

import com.onnjoy.dto.EntryRequest;
import com.onnjoy.model.Entry;
import com.onnjoy.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;

    public void saveEntry(String userEmail, EntryRequest request) {
        Entry entry = Entry.builder()
                .language(request.getLanguage())
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .build();

        entryRepository.save(entry);
    }
}
