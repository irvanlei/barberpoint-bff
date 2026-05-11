package com.barberpoint.bff.api.controllers;

import com.barberpoint.bff.application.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<?> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterCliente(@PathVariable Long id) {
        return clienteService.obterCliente(id);
    }

    @PostMapping
    public ResponseEntity<?> criarCliente(@RequestBody String payload) {
        return clienteService.criarCliente(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody String payload) {
        return clienteService.atualizarCliente(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        return clienteService.deletarCliente(id);
    }
}
