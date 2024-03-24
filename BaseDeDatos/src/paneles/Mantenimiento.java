package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import conexiones.Conexion;
import conexiones.Operaciones;

public class Mantenimiento extends JPanel {

	private static final long serialVersionUID = 1L;

	// Componentes de la ventana
	JPanel pMant;
	JPanel pInfoMant;
	ImageIcon iconoOriginal;
	Image scaledImage;
	ImageIcon icon;
	JButton btnAtras;
	JPanel pCent;
	JLabel titleTabla;
	JPanel pDer;
	JButton add;
	JButton borrar;
	JButton filtrar;
	JButton actualizar;

	// Atributos
	private Base b;
	private JPanel panelAntiguo;
	private JPanel panelMant;
	private DefaultTableModel model;
	private JTable tablaBase;
	private JScrollPane sc;
	private Conexion c;
	private Connection cn;
	private NuevaFila nv;
	private String nombreTabla = "";
	private JPanel panelFiltro;
	private boolean panelFiltroVisible = false;

	private JComboBox<String> comboBox;
	private JTextField txtValorFiltro;

	private JScrollPane jsc;

	/**
	 * Create the panel.
	 */
	public Mantenimiento() {
		// Inicializaciones
		pMant = new JPanel();
		pInfoMant = new JPanel();
		iconoOriginal = new ImageIcon("src/recursos/flecha_atras.png");
		scaledImage = iconoOriginal.getImage().getScaledInstance(15, 17, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledImage);
		titleTabla = new JLabel("Tabla");
		btnAtras = new JButton(icon);
		pCent = new JPanel();
		pCent.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pDer = new JPanel();
		pDer.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(0, 0, 0)));
		actualizar = new JButton("Actualizar fila");
		add = new JButton("Añadir fila");
		borrar = new JButton("Borrar fila");
		filtrar = new JButton("Filtrar búsqueda");

		// Parámetros de la ventana inicial
		setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, 654, 501);

		// Panel principal
		pMant.setBackground(new Color(255, 255, 255));
		pMant.setBounds(0, 0, 654, 501);
		pMant.setLayout(new BorderLayout(0, 0));
		pMant.add(pInfoMant, BorderLayout.NORTH);

		// Panel de arriba
		pInfoMant.setLayout(new BoxLayout(pInfoMant, BoxLayout.X_AXIS));

		titleTabla.setHorizontalAlignment(SwingConstants.CENTER);
		titleTabla.setFont(new Font("Tahoma", Font.BOLD, 13));
		titleTabla.setPreferredSize(new Dimension(135, 30));
		pInfoMant.add(Box.createRigidArea(new Dimension(235, 40)));
		pInfoMant.add(titleTabla);

		pInfoMant.add(Box.createRigidArea(new Dimension(250, 40)));

		btnAtras.setHorizontalAlignment(SwingConstants.RIGHT);
		pInfoMant.add(btnAtras);

		// Panel del centro
		pCent.setBackground(new Color(255, 255, 255));
		pMant.add(pCent, BorderLayout.CENTER);
		pCent.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Estás haciendo el mantenimiento de la base de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pCent.add(lblNewLabel);

		// Panel de la derecha
		pDer.setLayout(new BoxLayout(pDer, BoxLayout.Y_AXIS));
		pDer.add(Box.createRigidArea(new Dimension(150, 20)));

		JLabel titleDer = new JLabel("<html><u>Panel derecho</u></html>");
		titleDer.setFont(new Font("Tahoma", Font.BOLD, 13));
		titleDer.setHorizontalAlignment(SwingConstants.CENTER);
		titleDer.setAlignmentX(CENTER_ALIGNMENT);
		titleDer.setSize(new Dimension(150, 40));
		pDer.add(titleDer);

		// Botones de mantenimiento
		actualizar.setAlignmentX(0.6f);
		add.setAlignmentX(0.6f);
		borrar.setAlignmentX(0.6f);
		filtrar.setAlignmentX(0.5f);

		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(actualizar);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(add);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(borrar);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(filtrar);
		pDer.add(Box.createRigidArea(new Dimension(0, 5)));
		pMant.add(pDer, BorderLayout.EAST);

		panelFiltro = new JPanel();
		panelFiltro.setVisible(false);
		pDer.add(panelFiltro);
		panelFiltro.setLayout(null);

		String[] opciones = { "Opción 1", "Opción 2", "Opción 3", "Opción 4" };
		comboBox = new JComboBox<String>(opciones);
		comboBox.setBounds(10, 36, 130, 22);
		panelFiltro.add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Filtrar por:");
		lblNewLabel_1.setBounds(10, 11, 130, 14);
		panelFiltro.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Valor:");
		lblNewLabel_2.setBounds(10, 79, 46, 14);
		panelFiltro.add(lblNewLabel_2);

		txtValorFiltro = new JTextField();
		txtValorFiltro.setBounds(10, 104, 130, 20);
		panelFiltro.add(txtValorFiltro);
		txtValorFiltro.setColumns(10);

		JButton btnOkFiltro = new JButton("OK");
		btnOkFiltro.setBounds(84, 144, 56, 23);
		panelFiltro.add(btnOkFiltro);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));

		/*
		 * ACCIONES
		 */

		// Acción del boton de atrás
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b.getPanel().remove(jsc);
				b.getPanel().remove(Mantenimiento.this);
				b.getPanel().add(b.getPanelIzq(), BorderLayout.WEST);
				b.getPanel().add(b.getCenterPanel(), BorderLayout.CENTER);

				b.getPanel().revalidate();
				b.getPanel().repaint();
			}
		});

		// Acción del botón actualizar
		actualizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!nombreTabla.isEmpty()) {
					int selectedRow = tablaBase.getSelectedRow();
					if (selectedRow != -1) {
						TableColumnModel columnModel = tablaBase.getColumnModel();
						Object columnName = columnModel.getColumn(0).getHeaderValue();
						Object valor = tablaBase.getValueAt(selectedRow, 0);
						if (valor instanceof String) {
							valor = "'" + valor + "'";
						}

						nv = new NuevaFila();
						nv.setM(Mantenimiento.this);
						nv.setFilaSeleccionada(selectedRow);
						nv.setValores(cn, nombreTabla, false);
						nv.setArgumentos(columnName, valor);
						nv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						nv.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Seleccione una fila para actualizar");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado una tabla");
				}
			}
		});

		// Acción del botón de añadir fila
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!nombreTabla.isEmpty()) {
					nv = new NuevaFila();
					nv.setM(Mantenimiento.this);
					nv.setValores(cn, nombreTabla, true);
					nv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					nv.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado una tabla");
				}
			}
		});

		// Acción del botón borrar
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!nombreTabla.isEmpty()) {
					int selectedRow = tablaBase.getSelectedRow();
					if (selectedRow != -1) {
						int respuesta = JOptionPane.showConfirmDialog(null,
								"¿Está usted seguro de querer borrar está fila?", "Confirmación de eliminación",
								JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							TableColumnModel columnModel = tablaBase.getColumnModel();
							Object columnName = columnModel.getColumn(0).getHeaderValue();
							Object valor = tablaBase.getValueAt(selectedRow, 0);
							if (valor instanceof String) {
								valor = "'" + valor + "'";
							}
							if (Operaciones.borrarFila(cn, nombreTabla, columnName, valor)) {
								model.removeRow(selectedRow);
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado una tabla");
				}

			}
		});

		// Acción del botón filtrar
		filtrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!nombreTabla.isEmpty()) {
					filtrarBusqueda();
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado una tabla");
				}
			}
		});

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// Añadimos componetes a sus contenedores
		add(pMant);
	}

	// GETTERS Y SETTERS
	public JScrollPane getSc() {
		return sc;
	}

	public JTable getTablaBase() {
		return tablaBase;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public JPanel getPCent() {
		return pCent;
	}

	public void setB(Base b) {
		this.b = b;
		this.panelAntiguo = b.getPanelIzq();
		this.c = b.getCon();
		nuevoPanel();
	}

	// MÉTODOS PRIVADOS
	private void nuevoPanel() {
		// Creo el nuevo JPanel
		panelMant = new JPanel();
		panelMant.setLayout(new BoxLayout(panelMant, BoxLayout.Y_AXIS));
		panelMant.add(Box.createRigidArea(new Dimension(190, 15)));
		panelMant.setBackground(panelAntiguo.getBackground());

		JLabel titleMant = new JLabel("<html><u>Panel de mantenimiento</u></html>");
		titleMant.setHorizontalAlignment(SwingConstants.CENTER);
		titleMant.setFont(new Font("Times New Roman", Font.BOLD, 16));
		titleMant.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelMant.add(titleMant);

		panelMant.add(Box.createRigidArea(new Dimension(0, 25)));

		// Quito el antiugo Jpanel;
		b.getPanel().remove(panelAntiguo);
		b.getPanel().add(panelMant, BorderLayout.WEST);

		// Muestro los botones de las tablas
		addTablas();

		b.getPanel().revalidate();
		b.getPanel().repaint();
	}

	private void addTablas() {
		try {
			Connection con = c.getConnection();
			cn = con;
			DatabaseMetaData metaData = con.getMetaData();
			String catalog = con.getCatalog();
			ResultSet resultSet = metaData.getTables(catalog, null, "%", new String[] { "TABLE" });

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				if (!tableName.startsWith("trace_xe")) {

					JButton bot = new JButton("<html><u>" + tableName + "</u></html>");
					bot.setBackground(new Color(0, 0, 0, 0));
					bot.setRolloverEnabled(false);
					bot.setContentAreaFilled(false);
					bot.setBorderPainted(false);
					bot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					bot.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							mostrarTabla(con, tableName);
							nombreTabla = tableName;
						}
					});
					bot.setAlignmentX(0.6f);
					panelMant.add(bot);
					panelMant.add(Box.createRigidArea(new Dimension(0, 10)));
				}
			}

			panelMant.revalidate();
			panelMant.repaint();

			jsc = new JScrollPane(panelMant);
			b.getPanel().remove(b.getPanelIzq());
			b.getPanel().add(jsc, BorderLayout.WEST);
			b.getPanel().revalidate();
			b.getPanel().repaint();

		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@SuppressWarnings("serial")
	public void mostrarTabla(Connection c, String tNom) {
		titleTabla.setText(tNom);
		pCent.removeAll();
		try {
			// Sentencia y resultado
			Statement statement = c.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tNom);

			int numCol = resultSet.getMetaData().getColumnCount();

			// Nombre de las columnas
			String[] nombresColumnas = new String[numCol];
			ResultSet resCol = c.getMetaData().getColumns(null, null, tNom, null);

			int cont = 0;
			while (resCol.next()) {
				nombresColumnas[cont] = resCol.getString("COLUMN_NAME");
				cont++;
			}
			resCol.close();

			// Datos de la tabla
			List<Object[]> datos = new ArrayList<>();
			while (resultSet.next()) {
				Object[] filaDatos = new Object[numCol];
				for (int columna = 1; columna <= numCol; columna++) {
					filaDatos[columna - 1] = resultSet.getObject(columna);
				}
				datos.add(filaDatos);
			}
			resultSet.close();

			Object[][] datosArray = new Object[datos.size()][numCol];
			for (int i = 0; i < datos.size(); i++) {
				datosArray[i] = datos.get(i);
			}

			// Creación de la tabla y su vista
			model = new DefaultTableModel(datosArray, nombresColumnas) {
				@Override
				public boolean isCellEditable(int fila, int columna) {
					// Hacer que todas las celdas sean no editables
					return false;
				}
			};

			tablaBase = new JTable(model);
			sc = new JScrollPane(tablaBase);

			pCent.add(sc, BorderLayout.CENTER);
			pCent.revalidate();
			pCent.repaint();
		} catch (SQLException er) {
			System.out.println(er.getMessage());
		}
	}

	private void filtrarBusqueda() {
		// TODO
		if (panelFiltroVisible == false) {
			panelFiltro.setVisible(true);
			panelFiltroVisible = true;
		} else {
			panelFiltro.setVisible(false);
			panelFiltroVisible = false;
		}
	}
}
