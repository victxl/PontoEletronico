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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import listeners.DataChangeListener;
import model.entities.Funcionario;
import model.services.DepartamentoService;
import model.services.FuncionarioService;
import util.Alerts;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaDeFuncionarioController implements Initializable, DataChangeListener {

    public Button novo;

    @FXML
    private TableView<Funcionario> tableViewFuncionario;

    @FXML
    private TableColumn<Funcionario, Integer> tableColumnId;

    @FXML
    private TableColumn<Funcionario, String> tableColumnNome;

    @FXML
    private TableColumn<Funcionario, String> tableColumnEmail;

    @FXML
    private TableColumn<Funcionario, String> tableColumnCpf;

    @FXML
    private TableColumn<Funcionario, LocalTime> tableColumnHorarioExpediente;

    @FXML
    private TableColumn<Funcionario, Funcionario> tableColumnEDIT;

    @FXML
    private TableColumn<Funcionario, Funcionario> tableColumnREMOVE;

    private ObservableList<Funcionario> obsList;

    private FuncionarioService service;

    public void setFuncionarioService(FuncionarioService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnHorarioExpediente.setCellValueFactory(new PropertyValueFactory<>("horarioExpediente"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewFuncionario.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service está nulo");
        }
        List<Funcionario> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewFuncionario.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    @FXML
    private void onBtnNovo(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Funcionario obj = new Funcionario();
        createDialogForm(obj, "/com/pontoeletronico/FormFuncionario.fxml", parentStage);
    }

    private void createDialogForm(Funcionario obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            FormFuncionarioController controller = loader.getController();
            controller.setFuncionario(obj);
            controller.setServices(new FuncionarioService(), new DepartamentoService());
            controller.loadAssociatedObjects();
            controller.subscribeDataChangeListener(this::onDataChanged);
            controller.updateFormData();  // Atualiza os dados do formulário

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Funcionario data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Funcionario, Funcionario>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Funcionario obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/com/pontoeletronico/FormFuncionario.fxml", Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Funcionario, Funcionario>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Funcionario obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Funcionario obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service está nulo");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
