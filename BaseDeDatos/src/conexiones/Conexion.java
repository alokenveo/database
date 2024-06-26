package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Connection cn;
	private String cadenaConexion;
	private String databaseName;

	public Conexion(String dbName) {
		cadenaConexion = "jdbc:sqlserver://localhost:1433;databaseName=" + dbName
				+ ";integratedSecurity=true;trustServerCertificate=true";
		databaseName = dbName;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn = DriverManager.getConnection(cadenaConexion, "", "");
		} catch (Exception e) {
			cn = null;
		}

		return cn;
	}

	public String getDatabaseName() {
		return databaseName;
	}

}
