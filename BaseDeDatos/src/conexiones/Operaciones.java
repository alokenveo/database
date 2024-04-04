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
	public static boolean seleccionarCambioValvulas(Connection cn, DefaultTableModel dm, JTextPane txt) {
		if (cn != null) {
			String sentencia = "SELECT \r\n" + "    Herramientas.her_descripcion AS 'Descripción Herramienta',\r\n"
					+ "    Reparaciones.rep_nombre AS 'Nombre Reparación',\r\n"
					+ "    Locomotora.loc_nombre AS 'Nombre Locomotora'\r\n" + "FROM \r\n" + "    Herramientas \r\n"
					+ "JOIN \r\n" + "    Emplean  ON Herramientas.her_cod_herramienta = Emplean.emp_cod_herramienta\r\n"
					+ "JOIN \r\n" + "    Tareas  ON Emplean.emp_cod_reparacion = Tareas.tar_cod_reparacion\r\n"
					+ "JOIN \r\n"
					+ "    Reparaciones ON Tareas.tar_cod_reparacion = Reparaciones.rep_cod_reparacion\r\n"
					+ "JOIN \r\n"
					+ "    Locomotora  ON Reparaciones.rep_cod_locomotora = Locomotora.Loc_cod_locomotora\r\n"
					+ "WHERE \r\n" + "    Tareas.tar_nombre = 'Cambio de Válvulas'\r\n"
					+ "    AND Locomotora.loc_fecha_inicio >= '01/01/2008'\r\n"
					+ "    AND Locomotora.loc_fecha_inicio <= '31/05/2009'\r\n" + "ORDER BY \r\n"
					+ "    Locomotora.loc_nombre ASC;";
			try {
				Statement stmt = cn.createStatement();
				ResultSet rs = stmt.executeQuery(sentencia);

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

	public static boolean seleccionarMotoresDiesel(Connection cn) {
		if (cn != null) {
			String sentencia = "";

		}
		return false;
	}

	public static boolean obtenerReglajesValvulas(Connection cn) {
		if (cn != null) {
			String sentencia = "";
		}
		return false;
	}
}
