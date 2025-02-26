package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static final String url = "jdbc:mysql://localhost:3306/dbsistemaacademicofull";
	private static final String user = "root";
	private static final String password = "mysql123";

	private static Connection conn;

	public static Connection getConnection() {

		if (conn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");

				conn = DriverManager.getConnection(url, user, password);

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} catch (ClassNotFoundException ex) {
				System.out.println("Driver não encontrado!" + ex.getMessage());
				ex.printStackTrace();
			}
		}

		return conn;
	}

}
