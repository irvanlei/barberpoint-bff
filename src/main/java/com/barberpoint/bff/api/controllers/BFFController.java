package com.barberpoint.bff.api.controllers;

import com.barberpoint.bff.application.dtos.AggregatedDataDTO;
import com.barberpoint.bff.infrastructure.clients.MicroserviceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/bff")
public class BFFController {

    private static final Logger logger = LoggerFactory.getLogger(BFFController.class);

    @Autowired
    private MicroserviceClient microserviceClient;

    // ========== AGENDAMENTOS ==========

    @GetMapping("/agendamentos")
    public ResponseEntity<?> listarAgendamentos(
            @RequestParam(required = false) String clienteId,
            @RequestParam(required = false) String barbeiroId,
            @RequestParam(required = false) String status) {
        try {
            ResponseEntity<?> result = microserviceClient.getAgendamentos(clienteId, barbeiroId, status);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao listar agendamentos: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar agendamentos\"}");
        }
    }

    @GetMapping("/agendamentos/{id}")
    public ResponseEntity<?> obterAgendamento(@PathVariable String id) {
        try {
            ResponseEntity<?> result = microserviceClient.getAgendamentoById(id);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao obter agendamento {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar agendamento\"}");
        }
    }

    @PostMapping("/agendamentos")
    public ResponseEntity<?> criarAgendamento(@RequestBody String json) {
        try {
            ResponseEntity<?> result = microserviceClient.createAgendamento(json);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (HttpStatusCodeException e) {
            logger.error("Erro ao criar agendamento: {}", e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Erro ao criar agendamento: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao criar agendamento\"}");
        }
    }

    @DeleteMapping("/agendamentos/{id}")
    public ResponseEntity<?> deletarAgendamento(@PathVariable String id) {
        try {
            ResponseEntity<?> result = microserviceClient.deleteAgendamento(id);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (HttpStatusCodeException e) {
            logger.error("Erro ao deletar agendamento {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Erro ao deletar agendamento {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao deletar agendamento\"}");
        }
    }

    // ========== CLIENTES ==========

    @GetMapping("/clientes")
    public ResponseEntity<?> listarClientes() {
        try {
            ResponseEntity<?> result = microserviceClient.getClientes();
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao listar clientes: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar clientes\"}");
        }
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> obterCliente(@PathVariable Long id) {
        try {
            ResponseEntity<?> result = microserviceClient.getCliente(id);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao obter cliente {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar cliente\"}");
        }
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> criarCliente(@RequestBody String json) {
        try {
            ResponseEntity<?> result = microserviceClient.createCliente(json);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao criar cliente: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao criar cliente\"}");
        }
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody String json) {
        try {
            ResponseEntity<?> result = microserviceClient.updateCliente(id, json);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao atualizar cliente {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao atualizar cliente\"}");
        }
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        try {
            ResponseEntity<?> result = microserviceClient.deleteCliente(id);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao deletar cliente {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao deletar cliente\"}");
        }
    }

    // ========== BARBEIROS ==========

    @GetMapping("/barbeiros")
    public ResponseEntity<?> listarBarbeiros() {
        try {
            ResponseEntity<?> result = microserviceClient.getBarbeiros();
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao listar barbeiros: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar barbeiros\"}");
        }
    }

    @PostMapping("/barbeiros")
    public ResponseEntity<?> criarBarbeiro(@RequestBody String json) {
        try {
            ResponseEntity<?> result = microserviceClient.createBarbeiro(json);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao criar barbeiro: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao criar barbeiro\"}");
        }
    }

    @PutMapping("/barbeiros/{id}")
    public ResponseEntity<?> atualizarBarbeiro(@PathVariable Long id, @RequestBody String json) {
        try {
            ResponseEntity<?> result = microserviceClient.updateBarbeiro(id, json);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao atualizar barbeiro {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao atualizar barbeiro\"}");
        }
    }

    @DeleteMapping("/barbeiros/{id}")
    public ResponseEntity<?> deletarBarbeiro(@PathVariable Long id) {
        try {
            ResponseEntity<?> result = microserviceClient.deleteBarbeiro(id);
            return ResponseEntity.status(result.getStatusCode()).body(result.getBody());
        } catch (Exception e) {
            logger.error("Erro ao deletar barbeiro {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao deletar barbeiro\"}");
        }
    }

    // ========== DADOS AGREGADOS ==========

    @GetMapping("/aggregated-data")
    public ResponseEntity<AggregatedDataDTO> obterDadosAgregados(
            @RequestParam String clienteId,
            @RequestParam(required = false) String data) {

        // Validação de entrada
        if (clienteId == null || clienteId.trim().isEmpty()) {
            logger.warn("Parâmetro clienteId está vazio ou nulo");
            return ResponseEntity.badRequest().build();
        }

        try {
            Long parsedClienteId = Long.parseLong(clienteId);

            // Chamar serviços
            ResponseEntity<?> cliente = microserviceClient.getCliente(parsedClienteId);
            ResponseEntity<?> agendamentos = microserviceClient.getAgendamentos(clienteId, null, null);
            ResponseEntity<?> barbeiros = microserviceClient.getBarbeiros();
            ResponseEntity<?> relatorio = microserviceClient.gerarRelatorio(clienteId);

            // Agregar dados
            AggregatedDataDTO aggregated = new AggregatedDataDTO(
                    cliente.getBody(),
                    (List<?>) agendamentos.getBody(),
                    (List<?>) barbeiros.getBody(),
                    relatorio.getBody());

            return ResponseEntity.ok(aggregated);
        } catch (NumberFormatException e) {
            logger.error("Formato inválido para clienteId: {}", clienteId, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Erro ao agregar dados para clienteId {}: {}", clienteId, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("BFF está funcionando!");
    }
}
