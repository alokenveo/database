package conexiones;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import auxiliares.Campos;

public class Operaciones {
	public static boolean actualizarTabla(Connection cn, Object[] o) {
		if (cn != null) {
		}
		return false;
	}

	public static boolean insertarFila(Connection cn, String tNom, Campos[] c) {
		if (cn != null) {
			String sentencia = "INSERT INTO " + tNom + " VALUES(";
			for (int i = 0; i < c.length; i++) {
				if (((String) c[i].getValor()).length() > 3) {
					sentencia += "'" + c[i].getValor() + "',";
				} else {
					sentencia += c[i].getValor() + ",";
				}
			}
			sentencia = sentencia.substring(0, sentencia.length() - 1);
			sentencia += ")";
			try {
				Statement statement = cn.createStatement();
				int filasAfectadas = statement.executeUpdate(sentencia);
				if (filasAfectadas > 0) {
					return true;
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se puede añadir esta fila\n" + e.getMessage());
				return false;
			}
		}
		return false;
	}

	public static boolean borrarFila(Connection cn, String tNom,Object nomCol,Object val) {
		if (cn != null) {
			String sentencia="DELETE from "+tNom+" WHERE "+nomCol+"="+val;
			System.out.println(sentencia);
		}
		return false;
	}

	public static void seleccionarCambioValvulas(Connection cn) {
	}

	public static void seleccionarMotoresDiesel(Connection cn) {
	}

	public static void obtenerReglajesValvulas(Connection cn) {
	}
}
