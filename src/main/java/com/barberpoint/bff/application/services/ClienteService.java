package com.barberpoint.bff.application.services;

import com.barberpoint.bff.application.ports.out.MicroserviceClientPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final MicroserviceClientPort microserviceClient;

    public ClienteService(MicroserviceClientPort microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    public ResponseEntity<?> listarClientes() {
        return microserviceClient.getClientes();
    }

    public ResponseEntity<?> obterCliente(Long id) {
        return microserviceClient.getCliente(id);
    }

    public ResponseEntity<?> criarCliente(String payload) {
        return microserviceClient.createCliente(payload);
    }

    public ResponseEntity<?> atualizarCliente(Long id, String payload) {
        return microserviceClient.updateCliente(id, payload);
    }

    public ResponseEntity<?> deletarCliente(Long id) {
        return microserviceClient.deleteCliente(id);
    }
}
