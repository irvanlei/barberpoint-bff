package com.barberpoint.bff.application.services;

import com.barberpoint.bff.application.ports.out.MicroserviceClientPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    private final MicroserviceClientPort microserviceClient;

    public AgendamentoService(MicroserviceClientPort microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    public ResponseEntity<?> listarAgendamentos(String clienteId, String barbeiroId, String status) {
        return microserviceClient.getAgendamentos(clienteId, barbeiroId, status);
    }

    public ResponseEntity<?> obterAgendamentoPorId(String id) {
        return microserviceClient.getAgendamentoById(id);
    }

    public ResponseEntity<?> criarAgendamento(String payload) {
        return microserviceClient.createAgendamento(payload);
    }

    public ResponseEntity<?> deletarAgendamento(String id) {
        return microserviceClient.deleteAgendamento(id);
    }
}
