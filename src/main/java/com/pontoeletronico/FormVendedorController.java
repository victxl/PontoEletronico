package com.pontoeletronico;

import db.DbException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import listeners.DataChageListener;
import model.entities.Departamento;
import model.entities.Vendedor;
import model.exceptions.ValidacaoException;
import model.services.DepartamentoService;
import model.services.VendedorService;
import util.Alerts;
import util.Constraints;
import util.Utils;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class FormVendedorController implements Initializable {

    @FXML
    Button btnSalvar;
    @FXML
    Button btnCancelar;
    private Vendedor entity;
    private VendedorService service;
    private DepartamentoService departamentoService;
    private List<DataChageListener> dataChageListeners = new ArrayList<>();
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpDataNascimento;
    @FXML
    private TextField txtSalario;
    @FXML
    private ComboBox<Departamento> comboBoxDepartamento;
    @FXML
    private Label labelErroNome;
    @FXML
    private Label labelErroEmail;
    @FXML
    private Label labelErroDataNascimento;
    @FXML
    private Label labelErroSalario;
    private ObservableList<Departamento> obsList;


    public void setServices(VendedorService service, DepartamentoService departamentoService) {
        this.service = service;
        this.departamentoService = departamentoService;
    }

    public void setDEPARTAMENTO(Vendedor entity) {
        this.entity = entity;
    }

    public void subscribeDataChageListener(DataChageListener listener) {
        this.dataChageListeners.add(listener);
    }


    @FXML
    public void btnSalvarOnAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entidade esta nulla");
        }
        if (service == null) {
            throw new IllegalStateException("Service esta nula");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListenres();
            Utils.currentStage(event).close();
        } catch (ValidacaoException e) {
            setMensagemDeErro(e.getErros());
        } catch (DbException e) {
            Alerts.showAlert("Erro ao salvar", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListenres() {
        for (DataChageListener listener : dataChageListeners) {
            listener.dataChage();
        }
    }

    private Vendedor getFormData() {
        Vendedor obj = new Vendedor();

        ValidacaoException exception = new ValidacaoException("Erro ao validar os dados do departamento");

        obj.setId(Utils.tryParseInteger(txtID.getText()));

        obj.setNome(txtNome.getText());

        if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
            exception.addErro("nome", "O campo Nome, não pode estar vazio");
        }
        obj.setNome(txtNome.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addErro("email", "O campo  Email, não pode estar vazio");
        }
        obj.setEmail(txtEmail.getText());
        if (dpDataNascimento.getValue() == null) {
            exception.addErro("nascimento", "O campo Nascimento, não pode ser vazio");
        } else {
            Instant instant = Instant.from(dpDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setDataNascimento(Date.from(instant));
        }

        if (txtSalario.getText() == null || txtSalario.getText().trim().isEmpty()) {
            exception.addErro("salario", "O compo Salario, não pode ficar vazio");
        } else {
            try {
                double salario = Double.parseDouble(txtSalario.getText());
                obj.setSalario(salario);
            } catch (NumberFormatException e) {
                exception.addErro("salario", "O campo Salário deve ser um número válido");
            }
        }

        obj.setSalario(Utils.tryParseToDouble(txtSalario.getText()));

        obj.setDepartamento(comboBoxDepartamento.getValue());

        if (exception.getErros().size() > 0) {
            throw exception;
        }
        return obj;
    }

    @FXML
    public void btnCancelarOnAction(ActionEvent event) {
        System.out.println("Cancelar");
        Utils.currentStage(event).close();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtID);
        Constraints.setTextFieldMaxLength(txtNome, 70);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpDataNascimento, "dd/MM/yyyy");
        initializeComboBoxDepartamento();
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entidade esta nulla");
        }
        txtID.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());

        txtSalario.setText(String.format("%.2f", entity.getSalario()));
        Locale.setDefault(Locale.US);
        if (entity.getDataNascimento() != null) {

            dpDataNascimento.setValue(LocalDate.ofInstant(entity.getDataNascimento().toInstant(), ZoneId.systemDefault()));
        }
        if (entity.getDepartamento() == null) {
            comboBoxDepartamento.getSelectionModel().selectFirst();
        }
        comboBoxDepartamento.setValue(entity.getDepartamento());
    }

    public void loadAssociatedObjetcts() {
        if (departamentoService == null) {
            throw new IllegalStateException("DepartamentoService esta nula");
        }
        List<Departamento> list = departamentoService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartamento.setItems(obsList);
    }

    private void setMensagemDeErro(Map<String, String> erros) {
        Set<String> fields = erros.keySet();

        labelErroNome.setText((fields.contains("nome") ? erros.get("nome") : ""));
        labelErroEmail.setText((fields.contains("email") ? erros.get("email") : ""));
        labelErroSalario.setText((fields.contains("salario") ? erros.get("salario") : ""));
        labelErroDataNascimento.setText((fields.contains("nascimento") ? erros.get("nascimento") : ""));

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
