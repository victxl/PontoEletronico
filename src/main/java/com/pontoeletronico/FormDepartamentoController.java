package com.pontoeletronico;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import util.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class FormDepartamentoController implements Initializable {

    private Departamento entity;


    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNome;

    @FXML
    private Label labelErroNome;

    @FXML
    Button btnSalvar;

    public void setDEPARTAMENTO(Departamento entity) {
        this.entity = entity;
    }

    @FXML
    Button btnCancelar;

    @FXML
    public void btnSalvarOnAction(ActionEvent event) {
        System.out.println("Salvando");
    }

    @FXML
    public void btnCancelarOnAction(ActionEvent event) {
        System.out.println("Cancelar");
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
