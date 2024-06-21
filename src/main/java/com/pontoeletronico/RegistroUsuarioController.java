package com.pontoeletronico;

import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.Alerts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroUsuarioController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private ChoiceBox<String> choiceTipo;

    @FXML
    private void onBtnRegistrar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        String tipo = choiceTipo.getValue();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipo.isEmpty()) {
            Alerts.showAlert("Erro", null, "Preencha todos os campos.", Alert.AlertType.ERROR);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DB.getConnection();
            String sql = "INSERT INTO registereduser (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, tipo);
            stmt.executeUpdate();

            Alerts.showAlert("Sucesso", null, "Usuário registrado com sucesso.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            Alerts.showAlert("Erro de Banco de Dados", null, e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            DB.closeStatement(stmt);
            DB.closeConnection(conn);
        }
    }

    public void onBtnCancelar(ActionEvent actionEvent) {

    }
}
