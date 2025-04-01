package com.onnjoy.controller;

import com.onnjoy.model.Entry;
import com.onnjoy.model.User;
import com.onnjoy.repository.EntryRepository;
import com.onnjoy.repository.UserRepository;
import com.onnjoy.config.JwtUtil;
import com.onnjoy.service.MatchingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/entry")
@RequiredArgsConstructor
public class EntryController {

    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final MatchingService matchingService;

    @PostMapping
    public ResponseEntity<?> submitEntry(@RequestHeader("Authorization") String authHeader,
                                         @RequestBody EntryRequest request) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        User user = userOpt.get();

        // Check for 2 entry/week rule
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        int entryCount = entryRepository.countEntriesThisWeek(user.getId(), weekAgo);
        if (entryCount >= 2) {
            return ResponseEntity.badRequest().body("Limit reached: 2 entries per week allowed.");
        }

        // Create and save entry
        Entry entry = new Entry();
        entry.setUserId(user.getId()); //  Set user
        entry.setLanguage(request.language());
        entry.setText(request.text());
        entry.setCreatedAt(LocalDateTime.now());

        entryRepository.save(entry);

        // Trigger matching logic
        matchingService.matchTherapists(entry.getId());

        return ResponseEntity.ok(" Entry submitted and matching started.");
    }

    public record EntryRequest(String text, String language) {}
}
