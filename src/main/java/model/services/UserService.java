package model.services;
import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public boolean checkLogin(String email, String senha) {
        String query = "SELECT * FROM registereduser WHERE email = ? AND senha = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Retorna true se encontrar um usuário com o email e senha fornecidos
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
