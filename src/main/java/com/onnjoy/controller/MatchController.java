package com.onnjoy.controller;

import com.onnjoy.config.JwtUtil;
import com.onnjoy.model.Entry;
import com.onnjoy.model.User;
import com.onnjoy.repository.EntryRepository;
import com.onnjoy.repository.UserRepository;
import com.onnjoy.service.MatchQueryService;
import com.onnjoy.service.MatchingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final UserRepository userRepository;
    private final EntryRepository entryRepository;
    private final MatchingService matchingService;
    private final MatchQueryService matchQueryService;
    private final JwtUtil jwtUtil;

    // 1. Trigger match for a specific entry
    @PostMapping("/{entryId}")
    public ResponseEntity<?> match(@PathVariable Long entryId) {
        List<Map<String, Object>> results = matchingService.matchTherapists(entryId);
        return ResponseEntity.ok(results);
    }

    // 2. Return latest match results for user
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMatches(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("User not found");

        Optional<Entry> latestOpt = entryRepository.findTopByUserIdOrderByCreatedAtDesc(userOpt.get().getId());
        if (latestOpt.isEmpty()) return ResponseEntity.badRequest().body("No entries found for this user.");

        Entry latestEntry = latestOpt.get();
        List<?> matches = matchQueryService.getMatchesForEntry(latestEntry.getId());
        return ResponseEntity.ok(matches);
    }
}

