package com.pontoeletronico;

import db.DbIntegrityException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import listeners.DataChageListener;
import model.entities.Departamento;
import model.services.DepartamentoService;
import util.Alerts;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaDeDepartamentoController implements Initializable , DataChageListener {

    private DepartamentoService service;

    @FXML
    private TableView<Departamento> tableViewDepartamento;

    @FXML
    private TableColumn<Departamento, Integer> tableColumnId;

    @FXML
    private TableColumn<Departamento, String> tableColumnNome;

    @FXML
    private TableColumn<Departamento, Departamento> tableColumnEdit;

    @FXML
    private TableColumn<Departamento, Departamento> tableColumnDelete;

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
        initBtnEdit();
        initDeleteEntity();
    }


    private void createDialogForm(Departamento obj,String absoluteNome, Stage parentStage) {
        URL fxmlFile = getClass().getResource(absoluteNome);

        try {
            FXMLLoader loader = new FXMLLoader(fxmlFile);
            Pane pane = loader.load();

            FormDepartamentoController controller = loader.getController();
            controller.setDEPARTAMENTO(obj);
            controller.setDepartamentoService(new DepartamentoService());
            controller.subscribeDataChageListener(this);
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


    @Override
    public void dataChage() {
        updateTableView();
    }

    private void initBtnEdit() {
        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEdit.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
            private final Button btn = new Button("edit");
            @Override
            protected void updateItem(Departamento obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(btn);
                btn.setOnAction(
                        event -> createDialogForm(
                                obj, "/com/pontoeletronico/FormDepartamento.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initDeleteEntity() {
        tableColumnDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnDelete.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
            private final Button button = new Button("DELETAR");
            @Override
            protected void updateItem(Departamento obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                    button.setOnAction(event -> deleteEntity(obj));
            }
        });
    }
    private void deleteEntity(Departamento obj) {
       Optional<ButtonType> result = Alerts.showConfirmation("Confirmação","Você quer mesmo Deletar?");

       if (result.get() == ButtonType.OK) {
           if (service == null){
               throw new IllegalStateException("Service was null!");
           }
           try {
               service.delete(obj);
               updateTableView();
           }catch (DbIntegrityException e){
               Alerts.showAlert("Erro ao remover",null,e.getMessage(), Alert.AlertType.ERROR);
           }
       }


    }




}
