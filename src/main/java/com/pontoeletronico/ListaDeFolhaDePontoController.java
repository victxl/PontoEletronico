package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.FolhaDePonto;
import model.entities.Funcionario;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListaDeFolhaDePontoController implements Initializable {

    @FXML
    private TableView<FolhaDePonto> tableViewFolhaDePonto;
    @FXML
    private TableColumn<FolhaDePonto, Integer> tableColumnId;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnNome;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnData;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnHoraEntrada;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnHoraSaida;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnIntervaloInicio;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnIntervaloFim;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarFuncionario;

    private FuncionarioService funcionarioService;
    private FolhaDePontoService service;

    public void setFolhaDePontoService(FolhaDePontoService service) {
        this.service = service;
    }

    public void setFuncionarioService(FuncionarioService service) {
        this.funcionarioService = service;
    }

    @FXML
    private void onBtnBuscar() {
        if (txtBuscarFuncionario.getText().isEmpty()) {
            updateTableView();
        } else {
            Integer funcionarioId = Integer.parseInt(txtBuscarFuncionario.getText());
            List<FolhaDePonto> list = service.findByFuncionarioId(funcionarioId);
            for (FolhaDePonto ponto : list) {
                Funcionario funcionario = funcionarioService.findById(funcionarioId);
                if (funcionario != null) {
                    ponto.setFuncionarioNome(funcionario.getNome());
                }
            }
            tableViewFolhaDePonto.getItems().setAll(list);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("funcionarioNome"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnHoraEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tableColumnHoraSaida.setCellValueFactory(new PropertyValueFactory<>("horaSaida"));
        tableColumnIntervaloInicio.setCellValueFactory(new PropertyValueFactory<>("horaEntradaIntervalo"));
        tableColumnIntervaloFim.setCellValueFactory(new PropertyValueFactory<>("horaSaidaIntervalo"));
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<FolhaDePonto> list = service.findAll();
        for (FolhaDePonto ponto : list) {
            Funcionario funcionario = funcionarioService.findById(ponto.getFuncionarioId());
            if (funcionario != null) {
                ponto.setFuncionarioNome(funcionario.getNome());
            }
        }
        tableViewFolhaDePonto.getItems().setAll(list);
    }
}
