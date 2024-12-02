package com.airtribe.NewsAggregator.controller;

import com.airtribe.NewsAggregator.DTOs.PreferenceDTO;
import com.airtribe.NewsAggregator.exceptions.UserPreferenceNotFoundException;
import com.airtribe.NewsAggregator.service.PreferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class PreferenceController {
    @Autowired
    PreferenceService preferenceService;

    @PostMapping("/api/preferences")
    public ResponseEntity<?> setPreferences(Authentication authentication, @Valid @RequestBody PreferenceDTO preferenceDTO){
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authenticated");
        }
        String username = authentication.getName();
        preferenceService.addPreference(username,preferenceDTO);
        return ResponseEntity.ok("Preference added successfully");
    }

    @GetMapping("/api/preferences")
    public ResponseEntity<?> getPreferences(Authentication authentication) throws UserPreferenceNotFoundException{
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authenticated");
        }
        String username = authentication.getName();
        PreferenceDTO preference = preferenceService.getPreferences(username);
        if(preference == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Preference Set for UserName: " + username);
        }
        return ResponseEntity.ok(preference);
    }

    @PutMapping("/api/preferences")
    public ResponseEntity<?> updatePreferences(Authentication authentication,@Valid @RequestBody PreferenceDTO preferencesDto) throws UserPreferenceNotFoundException {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authenticated");
        }
        String username = authentication.getName();
        preferenceService.updatePreferences(username, preferencesDto);
        return ResponseEntity.ok("Preferences updated successfully");
    }
}
