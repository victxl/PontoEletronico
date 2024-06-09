package com.pontoeletronico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoService;
import util.Alerts;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListaDeDepartamentoController implements Initializable {

    private DepartamentoService service;

    @FXML
    private TableView<Departamento> tableViewDepartamento;

    @FXML
    private TableColumn<Departamento, Integer> tableColumnId;

    @FXML
    private TableColumn<Departamento, String> tableColumnNome;

    @FXML
    private Button btNovo;

    private ObservableList<Departamento> obsList;

    @FXML
    public void onBtnNovo(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Departamento obj = new Departamento();
        createDialogForm(obj,"/com/pontoeletronico/FormDepartamento.fxml",parentStage);
    }


    public void setDepartamentoService(DepartamentoService service) {
        this.service = service;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
 }
    private void initializeNodes(){

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));;




        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(service==null){
            throw new IllegalStateException("Service was null!");
        }
        List<Departamento> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartamento.setItems(obsList);
    }


    private void createDialogForm(Departamento obj,String absoluteNome, Stage parentStage) {
        URL fxmlFile = getClass().getResource(absoluteNome);

        try {
            FXMLLoader loader = new FXMLLoader(fxmlFile);
            Pane pane = loader.load();

            FormDepartamentoController controller = loader.getController();
            controller.setDEPARTAMENTO(obj);
            controller.setDepartamentoService(new DepartamentoService());
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Digite o nome do departamento");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}
