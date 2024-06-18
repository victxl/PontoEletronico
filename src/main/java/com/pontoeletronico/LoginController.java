package com.pontoeletronico;

import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.Alerts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private void setLoginButton(ActionEvent event) {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        if (authenticate(email, senha)) {
            try {
                Main.loadMainView("/com/pontoeletronico/MainView.fxml");
            } catch (Exception e) {
                Alerts.showAlert("Erro", null, "Erro ao carregar a tela principal.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Alerts.showAlert("Falha na Autenticação", null, "Usuário ou senha inválidos.", Alert.AlertType.ERROR);
        }
    }

    private boolean authenticate(String email, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();
            String sql = "SELECT * FROM registereduser WHERE email = ? AND senha = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            Alerts.showAlert("Erro de Banco de Dados", null, e.getMessage(), Alert.AlertType.ERROR);
            return false;
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
            DB.closeConnection(conn);
        }
    }
}
