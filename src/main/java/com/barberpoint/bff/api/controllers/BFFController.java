package com.barberpoint.bff.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff")
public class BFFController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("BFF está funcionando!");
    }
}
