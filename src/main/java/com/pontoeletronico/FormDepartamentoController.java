package com.pontoeletronico;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class FormDepartamentoController implements Initializable {

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNome;

    @FXML
    private Label labelErroNome;

    @FXML
    Button btnSalvar;

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


}
