package com.barberpoint.bff.application.services;

import com.barberpoint.bff.application.dtos.AggregatedDataDTO;
import com.barberpoint.bff.application.ports.out.MicroserviceClientPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class AggregatedDataService {

    private final MicroserviceClientPort microserviceClient;

    public AggregatedDataService(MicroserviceClientPort microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    public AggregatedDataDTO obterDadosAgregados(String clienteId, String data) {
        if (!StringUtils.hasText(clienteId)) {
            throw new IllegalArgumentException("clienteId é obrigatório");
        }

        Long clienteIdLong = parseClienteId(clienteId);

        ResponseEntity<?> clienteResponse = microserviceClient.getCliente(clienteIdLong);
        ResponseEntity<?> agendamentosResponse = microserviceClient.getAgendamentos(clienteId, null, null);
        ResponseEntity<?> barbeirosResponse = microserviceClient.getBarbeiros();
        ResponseEntity<?> relatorioResponse = microserviceClient.gerarRelatorio(clienteId);

        return new AggregatedDataDTO(
                clienteResponse.getBody(),
                castToList(agendamentosResponse.getBody()),
                castToList(barbeirosResponse.getBody()),
                relatorioResponse.getBody());
    }

    @SuppressWarnings("unchecked")
    private List<Object> castToList(Object source) {
        if (source instanceof List<?>) {
            return (List<Object>) source;
        }
        return Collections.emptyList();
    }

    private Long parseClienteId(String clienteId) {
        try {
            return Long.parseLong(clienteId);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("clienteId deve ser um número válido", ex);
        }
    }
}
