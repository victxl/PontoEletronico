package com.pontoeletronico;

import db.DbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import listeners.DataChageListener;
import model.entities.Vendedor;
import model.exceptions.ValidacaoException;
import model.services.VendedorService;
import util.Alerts;
import util.Constraints;
import util.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class FormVendedorController implements Initializable {

    private Vendedor entity;

    private VendedorService service;

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
    private Label labelErroNome;
    @FXML
    private Label labelErroEmail;
    @FXML
    private Label labelErroDataNascimento;
    @FXML
    private Label labelErroSalario;

    @FXML
    Button btnSalvar;

    public void setVendedorService(VendedorService service) {
        this.service = service;
    }

    public void setDEPARTAMENTO(Vendedor entity) {
        this.entity = entity;
    }

    public  void subscribeDataChageListener(DataChageListener listener) {
        this.dataChageListeners.add(listener);
    }

    @FXML
    Button btnCancelar;

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
       }
       catch (ValidacaoException e){
           setMensagemDeErro(e.getErros());
       }
       catch (DbException e) {
           Alerts.showAlert("Erro ao salvar",null,e.getMessage(), Alert.AlertType.ERROR);
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
            exception.addErro("nome","O campo não pode estar vazio");
        }
        obj.setNome(txtNome.getText());

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

    private void initializeNodes (){
        Constraints.setTextFieldInteger(txtID);
        Constraints.setTextFieldMaxLength(txtNome,70);
        Constraints.setTextFieldMaxLength(txtEmail,60);
        Utils.formatDatePicker(dpDataNascimento,"dd/MM/yyyy");
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entidade esta nulla");}
        txtID.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtSalario.setText(String.format("%.2f",entity.getSalario()));
       if (entity.getDataNascimento() != null) {
           dpDataNascimento.setValue(LocalDate.ofInstant(entity.getDataNascimento().toInstant(), ZoneId.systemDefault()));
       }
  }

    private void setMensagemDeErro(Map<String, String> erros){
        Set<String> fields = erros.keySet();

        if (fields.contains("nome")){
            labelErroNome.setText(erros.get("nome"));
        }

    }


}