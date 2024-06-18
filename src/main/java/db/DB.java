package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

	// Carrega as propriedades do arquivo db.properties
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	// Obtém uma nova conexão com o banco de dados
	public static Connection getConnection() {
		try {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			return DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	// Verifica o login do usuário
	public static boolean checkLogin(String nome, String senha) {
		String sql = "SELECT * FROM registeredUser WHERE nome = ? AND senha = ?";
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, nome);
			statement.setString(2, senha);
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next(); // Se houver algum resultado, significa que o login é válido
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	// Fecha um Statement
	public static void closeStatement(PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	// Fecha um ResultSet
	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeConnection(Connection conn) {
	}
}
