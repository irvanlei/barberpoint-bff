package com.barberpoint.bff.api.controllers;

import com.barberpoint.bff.application.dtos.AggregatedDataDTO;
import com.barberpoint.bff.application.services.AggregatedDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff")
public class AggregatedDataController {

    private final AggregatedDataService aggregatedDataService;

    public AggregatedDataController(AggregatedDataService aggregatedDataService) {
        this.aggregatedDataService = aggregatedDataService;
    }

    @GetMapping("/aggregated-data")
    public ResponseEntity<AggregatedDataDTO> obterDadosAgregados(
            @RequestParam String clienteId,
            @RequestParam(required = false) String data) {
        AggregatedDataDTO aggregatedData = aggregatedDataService.obterDadosAgregados(clienteId, data);
        return ResponseEntity.ok(aggregatedData);
    }
}
