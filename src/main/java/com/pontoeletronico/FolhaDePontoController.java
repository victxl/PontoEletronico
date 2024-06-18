package com.pontoeletronico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.FolhaDePonto;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;

public class FolhaDePontoController {

    @FXML
    private TableView<FolhaDePonto> tableViewFolhaDePonto;
    @FXML
    private TableColumn<FolhaDePonto, Integer> tableColumnFuncionarioId;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnData;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnHoraEntrada;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnHoraSaidaIntervalo;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnHoraEntradaIntervalo;
    @FXML
    private TableColumn<FolhaDePonto, String> tableColumnHoraSaida;

    private FolhaDePontoService service;

    public void setFolhaDePontoService(FolhaDePontoService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        tableColumnFuncionarioId.setCellValueFactory(new PropertyValueFactory<>("funcionarioId"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnHoraEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tableColumnHoraSaidaIntervalo.setCellValueFactory(new PropertyValueFactory<>("horaSaidaIntervalo"));
        tableColumnHoraEntradaIntervalo.setCellValueFactory(new PropertyValueFactory<>("horaEntradaIntervalo"));
        tableColumnHoraSaida.setCellValueFactory(new PropertyValueFactory<>("horaSaida"));
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        tableViewFolhaDePonto.setItems(FXCollections.observableArrayList(service.findAll()));
    }

    public void setFuncionarioService(FuncionarioService funcionarioService) {
        System.out.println(funcionarioService);
    }
}
