package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FolhaDePonto {
    private Integer id;
    private Integer funcionarioId;
    private LocalDate data;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private LocalTime horaEntradaIntervalo;
    private LocalTime horaSaidaIntervalo;

    // Propriedades para JavaFX
    private final ObjectProperty<LocalDate> dataProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> horaEntradaProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> horaSaidaProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> horaEntradaIntervaloProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> horaSaidaIntervaloProperty = new SimpleObjectProperty<>();

    // Getters e setters
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
        this.dataProperty.set(data);
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
        this.horaEntradaProperty.set(horaEntrada);
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        this.horaSaida = horaSaida;
        this.horaSaidaProperty.set(horaSaida);
    }

    public LocalTime getHoraEntradaIntervalo() {
        return horaEntradaIntervalo;
    }

    public void setHoraEntradaIntervalo(LocalTime horaEntradaIntervalo) {
        this.horaEntradaIntervalo = horaEntradaIntervalo;
        this.horaEntradaIntervaloProperty.set(horaEntradaIntervalo);
    }

    public LocalTime getHoraSaidaIntervalo() {
        return horaSaidaIntervalo;
    }

    public void setHoraSaidaIntervalo(LocalTime horaSaidaIntervalo) {
        this.horaSaidaIntervalo = horaSaidaIntervalo;
        this.horaSaidaIntervaloProperty.set(horaSaidaIntervalo);
    }

    // Métodos de propriedade para JavaFX
    public ObjectProperty<LocalDate> dataProperty() {
        return dataProperty;
    }

    public ObjectProperty<LocalTime> horaEntradaProperty() {
        return horaEntradaProperty;
    }

    public ObjectProperty<LocalTime> horaSaidaProperty() {
        return horaSaidaProperty;
    }

    public ObjectProperty<LocalTime> horaEntradaIntervaloProperty() {
        return horaEntradaIntervaloProperty;
    }

    public ObjectProperty<LocalTime> horaSaidaIntervaloProperty() {
        return horaSaidaIntervaloProperty;
    }
}
