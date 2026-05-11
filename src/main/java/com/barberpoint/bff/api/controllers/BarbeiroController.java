package com.barberpoint.bff.api.controllers;

import com.barberpoint.bff.application.services.BarbeiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/barbeiros")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    @GetMapping
    public ResponseEntity<?> listarBarbeiros() {
        return barbeiroService.listarBarbeiros();
    }

    @PostMapping
    public ResponseEntity<?> criarBarbeiro(@RequestBody String payload) {
        return barbeiroService.criarBarbeiro(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarBarbeiro(@PathVariable Long id, @RequestBody String payload) {
        return barbeiroService.atualizarBarbeiro(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarBarbeiro(@PathVariable Long id) {
        return barbeiroService.deletarBarbeiro(id);
    }
}
