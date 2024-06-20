package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.entities.FolhaDePonto;
import model.entities.Funcionario;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    @FXML
    private void onBtnExportar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Arquivo Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo Excel", "*.xlsx"));

        // Definindo um nome padrão para o arquivo
        fileChooser.setInitialFileName("exportacao.xlsx");

        // Abrindo o diálogo de escolha do arquivo
        File file = fileChooser.showSaveDialog(tableViewFolhaDePonto.getScene().getWindow());

        // Se o usuário escolheu um arquivo
        if (file != null) {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Folha de Ponto");

                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Nome");
                header.createCell(2).setCellValue("Data");
                header.createCell(3).setCellValue("Hora Entrada");
                header.createCell(4).setCellValue("Início Intervalo");
                header.createCell(5).setCellValue("Fim Intervalo");
                header.createCell(6).setCellValue("Hora Saída");

                int rowIndex = 1;
                for (FolhaDePonto folha : tableViewFolhaDePonto.getItems()) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(folha.getId());
                    row.createCell(1).setCellValue(folha.getFuncionarioNome());
                    row.createCell(2).setCellValue(folha.getData().toString());
                    row.createCell(3).setCellValue(folha.getHoraEntrada() != null ? folha.getHoraEntrada().toString() : "");
                    row.createCell(4).setCellValue(folha.getHoraEntradaIntervalo() != null ? folha.getHoraEntradaIntervalo().toString() : "");
                    row.createCell(5).setCellValue(folha.getHoraSaidaIntervalo() != null ? folha.getHoraSaidaIntervalo().toString() : "");
                    row.createCell(6).setCellValue(folha.getHoraSaida() != null ? folha.getHoraSaida().toString() : "");
                }

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Arquivo exportado com sucesso para " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao exportar o arquivo: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
