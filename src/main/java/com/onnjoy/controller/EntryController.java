package com.onnjoy.controller;

import com.onnjoy.dto.EntryRequest;
import com.onnjoy.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entry")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;

    @PostMapping
    public String submitEntry(@RequestBody EntryRequest request, Authentication auth) {
        String userEmail = auth.getName(); // extracted from JWT
        entryService.saveEntry(userEmail, request);
        return "Entry received and saved.";
    }
}
