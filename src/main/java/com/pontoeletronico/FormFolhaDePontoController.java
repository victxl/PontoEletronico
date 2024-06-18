package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.entities.FolhaDePonto;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;
import model.entities.Funcionario;

import java.time.LocalDate;
import java.time.LocalTime;

public class FormFolhaDePontoController {

    @FXML
    private TextField txtFuncionarioId;
    @FXML
    private TextField txtNomeFuncionario;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtHoraEntrada;
    @FXML
    private TextField txtHoraSaida;
    @FXML
    private TextField txtIntervaloInicio;
    @FXML
    private TextField txtIntervaloFim;

    private FolhaDePontoService folhaDePontoService;
    private FuncionarioService funcionarioService;
    private FolhaDePonto ponto;
    private int clickCount = 0;

    public void setFolhaDePontoService(FolhaDePontoService service) {
        this.folhaDePontoService = service;
    }

    public void setFuncionarioService(FuncionarioService service) {
        this.funcionarioService = service;
    }

    @FXML
    public void onBtBuscarFuncionarioAction() {
        String funcionarioIdText = txtFuncionarioId.getText();
        if (funcionarioIdText.isEmpty()) {
            showAlert("Erro", "O campo Funcionario ID está vazio");
            return;
        }

        Integer funcionarioId;
        try {
            funcionarioId = Integer.parseInt(funcionarioIdText);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Erro ao converter Funcionario ID para número: " + funcionarioIdText);
            return;
        }

        Funcionario funcionario = funcionarioService.findById(funcionarioId);
        if (funcionario == null) {
            showAlert("Erro", "Funcionário não encontrado: " + funcionarioIdText);
            return;
        }

        txtNomeFuncionario.setText(funcionario.getNome());

        ponto = folhaDePontoService.findPontoByFuncionarioId(funcionarioId);
        if (ponto == null) {
            ponto = new FolhaDePonto();
            ponto.setFuncionarioId(funcionarioId);
            ponto.setData(LocalDate.now());
        }

        showAlert("Sucesso", "Funcionário encontrado: " + funcionarioId);
    }

    @FXML
    public void onRegistrar() {
        if (ponto == null) {
            showAlert("Erro", "Nenhum funcionário selecionado. Por favor, busque o funcionário primeiro.");
            return;
        }

        LocalTime now = LocalTime.now();

        switch (clickCount) {
            case 0:
                ponto.setHoraEntrada(now);
                txtHoraEntrada.setText(now.toString());
                showAlert("Sucesso", "Hora de entrada registrada: " + now);
                break;
            case 1:
                ponto.setHoraEntradaIntervalo(now);
                txtIntervaloInicio.setText(now.toString());
                showAlert("Sucesso", "Hora de entrada no intervalo registrada: " + now);
                break;
            case 2:
                ponto.setHoraSaidaIntervalo(now);
                txtIntervaloFim.setText(now.toString());
                showAlert("Sucesso", "Hora de saída do intervalo registrada: " + now);
                break;
            case 3:
                ponto.setHoraSaida(now);
                txtHoraSaida.setText(now.toString());
                folhaDePontoService.save(ponto);
                ponto = null;
                clickCount = -1;
                showAlert("Sucesso", "Hora de saída registrada: " + now);
                break;
        }

        clickCount++;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
