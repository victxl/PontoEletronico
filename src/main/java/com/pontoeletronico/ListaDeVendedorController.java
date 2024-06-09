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
import model.entities.Vendedor;
import model.services.VendedorService;
import util.Alerts;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaDeVendedorController implements Initializable , DataChageListener {

    private VendedorService service;

    @FXML
    private TableView<Vendedor> tableViewVendedor;

    @FXML
    private TableColumn<Vendedor, Integer> tableColumnId;

    @FXML
    private TableColumn<Vendedor, String> tableColumnNome;

    @FXML
    private TableColumn<Vendedor, String> tableColumnEmail;

    @FXML
    private TableColumn<Vendedor, Date> tableColumnDataNascimento;

    @FXML
    private TableColumn<Vendedor, Double> tableColumnSalario;


    @FXML
    private TableColumn<Vendedor, Vendedor> tableColumnEdit;

    @FXML
    private TableColumn<Vendedor, Vendedor> tableColumnDelete;

    @FXML
    private Button btNovo;

    private ObservableList<Vendedor> obsList;



    @FXML
    public void onBtnNovo(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Vendedor obj = new Vendedor();
        createDialogForm(obj,"/com/pontoeletronico/FormVendedor.fxml",parentStage);
    }


    public void setVendedorService(VendedorService service) {
        this.service = service;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
 }
    private void initializeNodes(){

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        Utils.formatTableColumnDate(tableColumnDataNascimento,"dd/MM/yyyy");
        tableColumnSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        Utils.formatTableColumnDouble(tableColumnSalario, 2);

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewVendedor.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(service==null){
            throw new IllegalStateException("Service was null!");
        }
        List<Vendedor> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewVendedor.setItems(obsList);
        initBtnEdit();
        initDeleteEntity();
    }


    private void createDialogForm(Vendedor obj,String absoluteNome, Stage parentStage) {
        URL fxmlFile = getClass().getResource(absoluteNome);

//        try {
//            FXMLLoader loader = new FXMLLoader(fxmlFile);
//            Pane pane = loader.load();
//
//            FormVendedorController controller = loader.getController();
//            controller.setDEPARTAMENTO(obj);
//            controller.setVendedorService(new VendedorService());
//            controller.subscribeDataChageListener(this);
//            controller.updateFormData();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Digite o nome do vendedor");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Alerts.showAlert("IO Exception", "Erro ao carregar view", e.getMessage(), Alert.AlertType.ERROR);
//        }
    }


    @Override
    public void dataChage() {
        updateTableView();
    }

    private void initBtnEdit() {
        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEdit.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
            private final Button btn = new Button("edit");
            @Override
            protected void updateItem(Vendedor obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(btn);
                btn.setOnAction(
                        event -> createDialogForm(
                                obj, "/com/pontoeletronico/FormVendedor.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initDeleteEntity() {
        tableColumnDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnDelete.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
            private final Button button = new Button("DELETAR");
            @Override
            protected void updateItem(Vendedor obj, boolean empty) {
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
    private void deleteEntity(Vendedor obj) {
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
