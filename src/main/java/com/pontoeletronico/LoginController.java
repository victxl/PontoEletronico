package com.pontoeletronico;

import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.Alerts;
import model.entities.Login;

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

        Login login = authenticate(email, senha);
        if (login != null) {
            try {
                if ("admin".equals(login.getTipo())) {
                    Main.loadMainView("/com/pontoeletronico/MainViewAdmin.fxml");
                } else {
                    Main.loadMainView("/com/pontoeletronico/MainViewFuncionario.fxml");
                }
                MainViewController mainController = Main.getMainViewController();
                if (mainController != null) {
                    mainController.setUsuarioLogado(login);
                }
            } catch (Exception e) {
                Alerts.showAlert("Erro", null, "Erro ao carregar a tela principal.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Alerts.showAlert("Falha na Autenticação", null, "Usuário ou senha inválidos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onBtnRegistrar(ActionEvent event) {
        try {
            Main.loadMainView("/com/pontoeletronico/RegistroUsuario.fxml");
        } catch (Exception e) {
            Alerts.showAlert("Erro", null, "Erro ao carregar a tela de registro.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private Login authenticate(String email, String password) {
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

            if (rs.next()) {
                return new Login(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("tipo") // Capturar o valor de tipo
                );
            }
            return null;
        } catch (SQLException e) {
            Alerts.showAlert("Erro de Banco de Dados", null, e.getMessage(), Alert.AlertType.ERROR);
            return null;
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
            DB.closeConnection(conn);
        }
    }
}
