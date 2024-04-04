package conexiones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import auxiliares.Campos;

public class Operaciones {

	// Función para actualizar las filas de una tabla dada
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

	// Función para añadir nuevas filas a una tabla dada
	public static boolean insertarFila(Connection cn, String tNom, Campos[] c) {
		if (cn != null) {
			String sentencia = "INSERT INTO " + tNom + " VALUES(";
			for (int i = 0; i < c.length; i++) {
				if (!(c[i].getValor() instanceof Integer) && !c[i].getValor().equals("NULL")) {
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

	// Función para borrar filas de una determinada tabla
	public static boolean borrarFila(Connection cn, String tNom, Object nomCol, Object val) {
		if (cn != null) {
			String sentencia = "DELETE from " + tNom + " WHERE " + nomCol + "=" + val;
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

	// SENTENCIAS SQL
	public static boolean ejecutarSentencia(Connection cn,String sent, DefaultTableModel dm, JTextPane txt) {
		if (cn != null) {
			try {
				Statement stmt = cn.createStatement();
				ResultSet rs = stmt.executeQuery(sent);

				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				for (int column = 1; column <= columnCount; column++) {
					dm.addColumn(metaData.getColumnLabel(column));
				}

				// Agregar filas al modelo de tabla
				while (rs.next()) {
					Object[] row = new Object[columnCount];
					for (int i = 1; i <= columnCount; i++) {
						row[i - 1] = rs.getObject(i);
					}
					dm.addRow(row);
				}
				
				return true;
			} catch (SQLException e) {
				txt.setText(e.getMessage());
				return false;
			}
		}
		return false;
	}
}
