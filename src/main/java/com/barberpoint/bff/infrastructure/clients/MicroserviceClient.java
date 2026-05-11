package com.barberpoint.bff.infrastructure.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class MicroserviceClient {

    @Value("${ms.agendamentos.url:http://localhost:8081/api}")
    private String agendamentosUrl;

    @Value("${ms.clientes-barbeiros.url:http://localhost:8082/api}")
    private String clientesBarbeirosUrl;

    @Value("${azure.function.url:https://barberpoint-function.azurewebsites.net/api}")
    private String azureFunctionUrl;

    private final RestTemplate restTemplate;

    public MicroserviceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ========== AGENDAMENTOS ==========

    @CircuitBreaker(name = "agendamentosBreaker", fallbackMethod = "agendamentosFallback")
    @Retry(name = "agendamentosRetry")
    @TimeLimiter(name = "agendamentosLimiter")
    public ResponseEntity<?> getAgendamentos(String clienteId, String barbeiroId, String status) {
        String url = agendamentosUrl + "/agendamentos";
        String params = "";
        if (clienteId != null && !clienteId.isEmpty())
            params += "clienteId=" + clienteId;
        if (barbeiroId != null && !barbeiroId.isEmpty())
            params += (params.isEmpty() ? "" : "&") + "barbeiroId=" + barbeiroId;
        if (status != null && !status.isEmpty())
            params += (params.isEmpty() ? "" : "&") + "status=" + status;
        if (!params.isEmpty())
            url += "?" + params;
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<?> agendamentosFallback(String clienteId, String barbeiroId, String status, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Agendamentos temporariamente indisponível"));
    }

    @CircuitBreaker(name = "agendamentoIdBreaker", fallbackMethod = "agendamentoByIdFallback")
    public ResponseEntity<?> getAgendamentoById(String id) {
        String url = agendamentosUrl + "/agendamentos/" + id;
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<?> agendamentoByIdFallback(String id, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Agendamentos temporariamente indisponível"));
    }

    @CircuitBreaker(name = "createAgendamentoBreaker", fallbackMethod = "createAgendamentoFallback")
    public ResponseEntity<?> createAgendamento(String json) {
        String url = agendamentosUrl + "/agendamentos";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, entity, Object.class);
    }

    public ResponseEntity<?> createAgendamentoFallback(String json, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Agendamentos temporariamente indisponível"));
    }

    @CircuitBreaker(name = "deleteAgendamentoBreaker", fallbackMethod = "deleteAgendamentoFallback")
    public ResponseEntity<?> deleteAgendamento(String id) {
        String url = agendamentosUrl + "/agendamentos/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
    }

    public ResponseEntity<?> deleteAgendamentoFallback(String id, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Agendamentos temporariamente indisponível"));
    }

    // ========== CLIENTES ==========

    @CircuitBreaker(name = "clientesBreaker", fallbackMethod = "clientesFallback")
    public ResponseEntity<?> getClientes() {
        String url = clientesBarbeirosUrl + "/clientes";
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<?> clientesFallback(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Clientes-Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "clienteBreaker", fallbackMethod = "clienteFallback")
    public ResponseEntity<?> getCliente(Long clienteId) {
        String url = clientesBarbeirosUrl + "/clientes/" + clienteId;
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<?> clienteFallback(Long clienteId, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Clientes-Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "createClienteBreaker", fallbackMethod = "createClienteFallback")
    public ResponseEntity<?> createCliente(String json) {
        String url = clientesBarbeirosUrl + "/clientes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, entity, Object.class);
    }

    public ResponseEntity<?> createClienteFallback(String json, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Clientes-Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "updateClienteBreaker", fallbackMethod = "updateClienteFallback")
    public ResponseEntity<?> updateCliente(Long id, String json) {
        String url = clientesBarbeirosUrl + "/clientes/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    public ResponseEntity<?> updateClienteFallback(Long id, String json, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Clientes-Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "deleteClienteBreaker", fallbackMethod = "deleteClienteFallback")
    public ResponseEntity<?> deleteCliente(Long id) {
        String url = clientesBarbeirosUrl + "/clientes/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
    }

    public ResponseEntity<?> deleteClienteFallback(Long id, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Clientes-Barbeiros temporariamente indisponível"));
    }

    // ========== BARBEIROS ==========

    @CircuitBreaker(name = "barbeirosBreaker", fallbackMethod = "barbeirosFallback")
    public ResponseEntity<?> getBarbeiros() {
        String url = clientesBarbeirosUrl + "/barbeiros";
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<?> barbeirosFallback(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "createBarbeiroBreaker", fallbackMethod = "createBarbeiroFallback")
    public ResponseEntity<?> createBarbeiro(String json) {
        String url = clientesBarbeirosUrl + "/barbeiros";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, entity, Object.class);
    }

    public ResponseEntity<?> createBarbeiroFallback(String json, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "updateBarbeiroBreaker", fallbackMethod = "updateBarbeiroFallback")
    public ResponseEntity<?> updateBarbeiro(Long id, String json) {
        String url = clientesBarbeirosUrl + "/barbeiros/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    public ResponseEntity<?> updateBarbeiroFallback(Long id, String json, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Barbeiros temporariamente indisponível"));
    }

    @CircuitBreaker(name = "deleteBarbeiroBreaker", fallbackMethod = "deleteBarbeiroFallback")
    public ResponseEntity<?> deleteBarbeiro(Long id) {
        String url = clientesBarbeirosUrl + "/barbeiros/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
    }

    public ResponseEntity<?> deleteBarbeiroFallback(Long id, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "MS Barbeiros temporariamente indisponível"));
    }

    // ========== RELATÓRIO ==========

    @CircuitBreaker(name = "relatorioBreaker", fallbackMethod = "relatorioFallback")
    public ResponseEntity<?> gerarRelatorio(String agendamentoId) {
        String url = azureFunctionUrl + "/reports/schedule-report";
        return restTemplate.postForEntity(url, "{\"agendamentoId\":\"" + agendamentoId + "\"}", Object.class);
    }

    public ResponseEntity<?> relatorioFallback(String agendamentoId, Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonMap("error", "Azure Function temporariamente indisponível"));
    }
}
