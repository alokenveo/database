package conexiones;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import auxiliares.Campos;

public class Operaciones {
	public static boolean actualizarTabla(Connection cn, String tNom, Campos[] c, int colModificada, Object nomCol,
			Object val) {
		if (cn != null) {
			StringBuilder sentencia = new StringBuilder("UPDATE " + tNom + " SET ");
			sentencia.append(c[colModificada].getNomCampo()).append(" = '").append(c[colModificada].getValor())
					.append("'");
			sentencia.append(" WHERE ").append(nomCol).append(" = ").append(val);

			try {
				Statement sent = cn.createStatement();
				sent.executeUpdate(sentencia.toString());
				return true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se puede actualizar esta fila\n" + e.getMessage());
				return false;
			}
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

	public static boolean borrarFila(Connection cn, String tNom, Object nomCol, Object val) {
		if (cn != null) {
			String sentencia = "DELETE from " + tNom + " WHERE " + nomCol + "=" + val;
			System.out.println(sentencia);
			try {
				Statement declaración = cn.createStatement();
				int filasEliminadas = declaración.executeUpdate(sentencia);
				if (filasEliminadas > 0) {
					return true;
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se puede borrar esta fila\n" + e.getMessage());
				return false;
			}
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
