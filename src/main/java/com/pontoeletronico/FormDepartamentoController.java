package com.pontoeletronico;

import db.DbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import listeners.DataChageListener;
import model.entities.Departamento;
import model.services.DepartamentoService;
import util.Alerts;
import util.Constraints;
import util.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormDepartamentoController implements Initializable {

    private Departamento entity;

    private DepartamentoService service;

    private List<DataChageListener> dataChageListeners = new ArrayList<>();

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNome;

    @FXML
    private Label labelErroNome;

    @FXML
    Button btnSalvar;

    public void setDepartamentoService(DepartamentoService service) {
        this.service = service;
    }

    public void setDEPARTAMENTO(Departamento entity) {
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
       catch (DbException e) {
           Alerts.showAlert("Erro ao salvar",null,e.getMessage(), Alert.AlertType.ERROR);
       }
    }

    private void notifyDataChangeListenres() {
        for (DataChageListener listener : dataChageListeners) {
            listener.dataChage();
        }
    }

    private Departamento getFormData() {
        Departamento obj = new Departamento();

        obj.setId(Utils.tryParseInteger(txtID.getText()));
        obj.setNome(txtNome.getText());

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
        Constraints.setTextFieldMaxLength(txtNome,30);
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entidade esta nulla");}
        txtID.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());

    }


}
