package com.pontoeletronico;

import db.DbException;

import listeners.DataChangeListener;
import util.Alerts;
import util.Constraints;
import util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.util.Callback;
import model.entities.Departamento;
import model.entities.Funcionario;
import model.exceptions.ValidacaoException;
import model.services.DepartamentoService;
import model.services.FuncionarioService;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class FormFuncionarioController implements Initializable {

    private Funcionario entity;

    private FuncionarioService service;

    private DepartamentoService departamentoService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private TextField txtHorarioExpediente;

    @FXML
    private ComboBox<Departamento> comboBoxDepartamento;

    @FXML
    private Label labelErrorNome;

    @FXML
    private Label labelErrorCpf;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorDataNascimento;

    @FXML
    private Label labelErrorHorarioExpediente;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    private ObservableList<Departamento> obsList;

    public void setFuncionario(Funcionario entity) {
        this.entity = entity;
    }

    public void setServices(FuncionarioService service, DepartamentoService departamentoService) {
        this.service = service;
        this.departamentoService = departamentoService;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSalvarAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidacaoException e) {
            setErrorMessages(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onBtCancelarAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Funcionario getFormData() {
        Funcionario obj = new Funcionario();

        ValidacaoException exception = new ValidacaoException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
            exception.addError("nome", "Field can't be empty");
        }
        obj.setNome(txtNome.getText());

        if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
            exception.addError("cpf", "Field can't be empty");
        }
        obj.setCpf(txtCpf.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addError("email", "Field can't be empty");
        }
        obj.setEmail(txtEmail.getText());

        if (dpDataNascimento.getValue() == null) {
            exception.addError("dataNascimento", "Field can't be empty");
        } else {
            LocalDate localDate = dpDataNascimento.getValue();
            Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            obj.setDataNascimento(localDate);
        }

        if (txtHorarioExpediente.getText() == null || txtHorarioExpediente.getText().trim().equals("")) {
            exception.addError("horarioExpediente", "Field can't be empty");
        } else {
            obj.setHorarioExpediente(LocalTime.parse(txtHorarioExpediente.getText()));
        }

        obj.setDepartamento(comboBoxDepartamento.getValue());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return obj;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtNome, 70);
        Constraints.setTextFieldMaxLength(txtCpf, 14);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpDataNascimento, "dd/MM/yyyy");

        initializeComboBoxDepartamento();
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtCpf.setText(entity.getCpf());
        txtEmail.setText(entity.getEmail());

        if (entity.getDataNascimento() != null) {
            dpDataNascimento.setValue(entity.getDataNascimento());
        }

        if (entity.getHorarioExpediente() != null) {
            txtHorarioExpediente.setText(entity.getHorarioExpediente().toString());
        }

        if (entity.getDepartamento() == null) {
            comboBoxDepartamento.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartamento.setValue(entity.getDepartamento());
        }
    }


    public void loadAssociatedObjects() {
        if (departamentoService == null) {
            throw new IllegalStateException("DepartamentoService was null");
        }
        List<Departamento> list = departamentoService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartamento.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorNome.setText((fields.contains("nome") ? errors.get("nome") : ""));
        labelErrorCpf.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
        labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
        labelErrorDataNascimento.setText((fields.contains("dataNascimento") ? errors.get("dataNascimento") : ""));
        labelErrorHorarioExpediente.setText((fields.contains("horarioExpediente") ? errors.get("horarioExpediente") : ""));
    }

    private void initializeComboBoxDepartamento() {
        Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
            @Override
            protected void updateItem(Departamento item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNome());
            }
        };
        comboBoxDepartamento.setCellFactory(factory);
        comboBoxDepartamento.setButtonCell(factory.call(null));
    }
}
