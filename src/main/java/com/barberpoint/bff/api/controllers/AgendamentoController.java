package com.barberpoint.bff.api.controllers;

import com.barberpoint.bff.application.services.AgendamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public ResponseEntity<?> listarAgendamentos(
            @RequestParam(required = false) String clienteId,
            @RequestParam(required = false) String barbeiroId,
            @RequestParam(required = false) String status) {
        return agendamentoService.listarAgendamentos(clienteId, barbeiroId, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterAgendamento(@PathVariable String id) {
        return agendamentoService.obterAgendamentoPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody String payload) {
        return agendamentoService.criarAgendamento(payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAgendamento(@PathVariable String id) {
        return agendamentoService.deletarAgendamento(id);
    }
}
