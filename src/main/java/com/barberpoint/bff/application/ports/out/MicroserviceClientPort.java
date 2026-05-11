package com.barberpoint.bff.application.ports.out;

import org.springframework.http.ResponseEntity;

public interface MicroserviceClientPort {

    ResponseEntity<?> getAgendamentos(String clienteId, String barbeiroId, String status);

    ResponseEntity<?> getAgendamentoById(String id);

    ResponseEntity<?> createAgendamento(String json);

    ResponseEntity<?> deleteAgendamento(String id);

    ResponseEntity<?> getClientes();

    ResponseEntity<?> getCliente(Long clienteId);

    ResponseEntity<?> createCliente(String json);

    ResponseEntity<?> updateCliente(Long id, String json);

    ResponseEntity<?> deleteCliente(Long id);

    ResponseEntity<?> getBarbeiros();

    ResponseEntity<?> createBarbeiro(String json);

    ResponseEntity<?> updateBarbeiro(Long id, String json);

    ResponseEntity<?> deleteBarbeiro(Long id);

    ResponseEntity<?> gerarRelatorio(String agendamentoId);
}
