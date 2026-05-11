package com.barberpoint.bff.application.dtos;

import java.util.List;

public class AggregatedDataDTO {

    private Object cliente;
    private List<Object> agendamentos;
    private List<Object> barbeiros;
    private Object relatorio;

    public AggregatedDataDTO() {
    }

    public AggregatedDataDTO(Object cliente, List<Object> agendamentos, List<Object> barbeiros, Object relatorio) {
        this.cliente = cliente;
        this.agendamentos = agendamentos;
        this.barbeiros = barbeiros;
        this.relatorio = relatorio;
    }

    public Object getCliente() {
        return cliente;
    }

    public void setCliente(Object cliente) {
        this.cliente = cliente;
    }

    public List<Object> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Object> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<Object> getBarbeiros() {
        return barbeiros;
    }

    public void setBarbeiros(List<Object> barbeiros) {
        this.barbeiros = barbeiros;
    }

    public Object getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Object relatorio) {
        this.relatorio = relatorio;
    }
}
