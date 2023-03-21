package com.example.taxibooking.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestRestApi {

    @GetMapping("/testData")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    public Map<String, Object> dataTest(Authentication authentication) {
        return Map.of(
                "Test:", "Data Test",
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities());
    }

    @PostMapping("/saveData")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Map<String, Object> saveData(Authentication authentication, String data) {
        return Map.of("dataSaved", data);
    }
}
