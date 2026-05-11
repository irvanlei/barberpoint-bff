package com.barberpoint.bff.application.dtos;

import java.util.List;

public class AggregatedDataDTO {

    private Object cliente;
    private List<?> agendamentos;
    private List<?> barbeiros;
    private Object relatorio;

    // Constructors
    public AggregatedDataDTO() {
    }

    public AggregatedDataDTO(Object cliente, List<?> agendamentos, List<?> barbeiros, Object relatorio) {
        this.cliente = cliente;
        this.agendamentos = agendamentos;
        this.barbeiros = barbeiros;
        this.relatorio = relatorio;
    }

    // Getters and Setters
    public Object getCliente() {
        return cliente;
    }

    public void setCliente(Object cliente) {
        this.cliente = cliente;
    }

    public List<?> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<?> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<?> getBarbeiros() {
        return barbeiros;
    }

    public void setBarbeiros(List<?> barbeiros) {
        this.barbeiros = barbeiros;
    }

    public Object getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Object relatorio) {
        this.relatorio = relatorio;
    }
}
