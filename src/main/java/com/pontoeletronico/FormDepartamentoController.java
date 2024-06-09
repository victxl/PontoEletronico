package com.pontoeletronico;

import db.DbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.services.DepartamentoService;
import util.Alerts;
import util.Constraints;
import util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class FormDepartamentoController implements Initializable {

    private Departamento entity;

    private DepartamentoService service;
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
           Utils.currentStage(event).close();
       }
       catch (DbException e) {
           Alerts.showAlert("Erro ao salvar",null,e.getMessage(), Alert.AlertType.ERROR);
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
