package com.onnjoy.controller;

import com.onnjoy.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/{entryId}")
    public List<Map<String, Object>> match(@PathVariable Long entryId) {
        return matchingService.matchTherapists(entryId);
    }
}
