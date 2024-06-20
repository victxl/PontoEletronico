package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class FolhaDePonto {

    private Integer id;
    private Integer funcionarioId;
    private LocalDate data;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private LocalTime horaEntradaIntervalo;
    private LocalTime horaSaidaIntervalo;
    private String funcionarioNome;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public LocalTime getHoraEntradaIntervalo() {
        return horaEntradaIntervalo;
    }

    public void setHoraEntradaIntervalo(LocalTime horaEntradaIntervalo) {
        this.horaEntradaIntervalo = horaEntradaIntervalo;
    }

    public LocalTime getHoraSaidaIntervalo() {
        return horaSaidaIntervalo;
    }

    public void setHoraSaidaIntervalo(LocalTime horaSaidaIntervalo) {
        this.horaSaidaIntervalo = horaSaidaIntervalo;
    }

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }
}
