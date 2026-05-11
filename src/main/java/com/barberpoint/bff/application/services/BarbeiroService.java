package com.barberpoint.bff.application.services;

import com.barberpoint.bff.application.ports.out.MicroserviceClientPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BarbeiroService {

    private final MicroserviceClientPort microserviceClient;

    public BarbeiroService(MicroserviceClientPort microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    public ResponseEntity<?> listarBarbeiros() {
        return microserviceClient.getBarbeiros();
    }

    public ResponseEntity<?> criarBarbeiro(String payload) {
        return microserviceClient.createBarbeiro(payload);
    }

    public ResponseEntity<?> atualizarBarbeiro(Long id, String payload) {
        return microserviceClient.updateBarbeiro(id, payload);
    }

    public ResponseEntity<?> deletarBarbeiro(Long id) {
        return microserviceClient.deleteBarbeiro(id);
    }
}
