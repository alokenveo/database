package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import auxiliares.Campos;
import conexiones.Operaciones;

public class NuevaFila extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();
	private Connection cn;
	private String nombreTabla;
	private Campos[] campos;
	private JTextField[] fields;
	private boolean camposVacios = false;
	private Mantenimiento m;
	private boolean nuevo;
	private int filaSeleccionada;
	private Object columnName;
	private Object valor;
	private JLabel lab;

	/**
	 * Create the dialog.
	 */
	public NuevaFila() {
		setTitle("Nueva Fila");
		// setBounds(550, 130, 310, 381);
		setSize(310, 381);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 0, 0)));
		JScrollPane vista = new JScrollPane(contenido);
		vista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(vista, BorderLayout.CENTER);
		contenido.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (nuevo) {
							// Para añadir una nueva fila
							if (campos != null) {
								for (int i = 0; i < campos.length; i++) {
									campos[i].setValor(fields[i].getText());

									// Si hay algún campo que no se ha rellenado, pongo esta bandera
									if (fields[i].getText().isEmpty()) {
										setCamposVacios(true);
										campos[i].setValor("NULL");
									}
								}
								if (Operaciones.insertarFila(cn, nombreTabla, campos)) {
									dispose();
									Object[] valores = new Object[campos.length];
									for (int i = 0; i < valores.length; i++) {
										valores[i] = campos[i].getValor();
									}
									m.getModel().addRow(valores);
									TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m.getModel());
									m.getTablaBase().setRowSorter(sorter);
									sorter.toggleSortOrder(0);
								}
							}
						} else {
							// Para actualizar la fila
							for (int i = 0; i < campos.length; i++) {
								if (campos[i].getValor() == null) {
									campos[i].setValor(fields[i].getText());
									if (Operaciones.actualizarTabla(cn, nombreTabla, campos, i, columnName, valor)) {
										m.mostrarTabla(cn, nombreTabla);
									}
								} else if (!campos[i].getValor().equals(fields[i].getText())) {
									campos[i].setValor(fields[i].getText());
									if (Operaciones.actualizarTabla(cn, nombreTabla, campos, i, columnName, valor)) {
										m.mostrarTabla(cn, nombreTabla);
									}
								}
								dispose();
							}
						}
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}

		JPanel pArriba = new JPanel();
		pArriba.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		getContentPane().add(pArriba, BorderLayout.NORTH);
		pArriba.setLayout(new BoxLayout(pArriba, BoxLayout.X_AXIS));
		pArriba.add(Box.createRigidArea(new Dimension(0, 45)));

		lab = new JLabel("<html><u>Añadir nueva fila</u></html>");
		lab.setAlignmentX(Component.CENTER_ALIGNMENT);
		lab.setFont(new Font("Tahoma", Font.BOLD, 15));
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		pArriba.add(lab);
	}

	public void setValores(Connection cn, String n, boolean nuevo) {
		this.cn = cn;
		this.nombreTabla = n;
		rellenarTabla(nuevo);
		this.nuevo = nuevo;
	}

	public void setM(Mantenimiento m) {
		this.m = m;
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}

	public void setFilaSeleccionada(int filaSeleccionada) {
		this.filaSeleccionada = filaSeleccionada;
	}

	public boolean isCamposVacios() {
		return camposVacios;
	}

	public void setCamposVacios(boolean camposVacios) {
		this.camposVacios = camposVacios;
	}

	private void rellenarTabla(boolean nuevo) {
		if (nuevo)
			try {
				// 1º Recogemos el número de datos que vamos a introducir
				Statement statement = cn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM " + nombreTabla);
				ResultSetMetaData metadata = resultSet.getMetaData();
				int numeroCol = metadata.getColumnCount();
				contenido.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.WEST;
				gbc.insets = new Insets(5, 5, 5, 5);

				// 2º Obtennemos el número de los campos
				campos = new Campos[numeroCol];
				fields = new JTextField[numeroCol];
				DatabaseMetaData meta = cn.getMetaData();
				resultSet = meta.getColumns(null, null, nombreTabla, null);
				int cont = 0;
				while (resultSet.next()) {
					String nomCampo = resultSet.getString("COLUMN_NAME");
					campos[cont] = new Campos();
					campos[cont].setNomCampo(nomCampo);

					// 3º Añadimos los campos a rellenar
					gbc.gridx = 0;
					gbc.gridy = cont;
					contenido.add(new JLabel(campos[cont].getNomCampo() + ": "), gbc);

					fields[cont] = new JTextField();
					fields[cont].setPreferredSize(new Dimension(135, 30));
					gbc.gridx = 1;
					contenido.add(fields[cont], gbc);

					cont++;
				}
				contenido.revalidate();
				contenido.repaint();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		else {
			try {
				// 1º Recogemos el número de datos que vamos a introducir
				lab.setText("<html><u>Actualizar fila</u></html>");
				lab.setAlignmentX(Component.CENTER_ALIGNMENT);
				lab.setFont(new Font("Tahoma", Font.BOLD, 15));
				lab.setHorizontalAlignment(SwingConstants.CENTER);

				Statement statement = cn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM " + nombreTabla);
				ResultSetMetaData metadata = resultSet.getMetaData();
				int numeroCol = metadata.getColumnCount();
				contenido.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.WEST;
				gbc.insets = new Insets(5, 5, 5, 5);

				// 2º Obtennemos el número de los campos
				campos = new Campos[numeroCol];
				fields = new JTextField[numeroCol];
				DatabaseMetaData meta = cn.getMetaData();
				resultSet = meta.getColumns(null, null, nombreTabla, null);
				int cont = 0;
				while (resultSet.next()) {
					String nomCampo = resultSet.getString("COLUMN_NAME");
					campos[cont] = new Campos();
					campos[cont].setNomCampo(nomCampo);

					// 3º Añadimos los campos a rellenar
					gbc.gridx = 0;
					gbc.gridy = cont;
					contenido.add(new JLabel(campos[cont].getNomCampo() + ": "), gbc);

					fields[cont] = new JTextField();
					fields[cont].setPreferredSize(new Dimension(135, 30));
					gbc.gridx = 1;
					contenido.add(fields[cont], gbc);

					cont++;
				}
				ResultSet res = statement.executeQuery("SELECT * FROM " + nombreTabla);
				cont = 0;
				boolean filaEncontrada = false;

				while (res.next() && !filaEncontrada) {
					if (cont == filaSeleccionada) {
						filaEncontrada = true;
						for (int i = 0; i < fields.length; i++) {
							fields[i].setText(res.getString(i + 1));
							campos[i].setValor(res.getString(i + 1));
						}
					}
					cont++;
				}

				contenido.revalidate();
				contenido.repaint();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void setArgumentos(Object columnName, Object valor) {
		this.columnName = columnName;
		this.valor = valor;
	}
}
