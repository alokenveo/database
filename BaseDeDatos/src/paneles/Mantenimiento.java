package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

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

	// Atributos
	private Base b;
	private JPanel panelAntiguo;
	private JPanel panelMant;
	DefaultTableModel model;
	JTable tablaBase;
	private Conexion c;
	private Connection cn;
	private NuevaFila nv;
	private String nombreTabla;

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
		titleDer.setPreferredSize(new Dimension(150, 40));
		pDer.add(titleDer);

		// Botones de mantenimiento
		add.setAlignmentX(0.6f);
		borrar.setAlignmentX(0.6f);
		filtrar.setAlignmentX(0.5f);

		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(add);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(borrar);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pDer.add(filtrar);
		pDer.add(Box.createRigidArea(new Dimension(0, 20)));
		pMant.add(pDer, BorderLayout.EAST);

		/*
		 * ACCIONES
		 */

		// Acción del boton de atrás
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Acción del botón de añadir fila
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nv = new NuevaFila();
				nv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				nv.setVisible(true);
			}
		});

		// Acción del botón borrar
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tablaBase.getSelectedRow();
				if (selectedRow != -1) {
					model.removeRow(selectedRow);
					Operaciones.borrarFila(cn, nombreTabla);
				}
			}
		});

		// Acción del botón filtrar
		filtrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				filtrarBusqueda();
			}
		});

		// Añadimos componetes a sus contenedores
		add(pMant);
	}

	// SETTERS
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
		panelMant.add(Box.createRigidArea(new Dimension(190, 20)));
		panelMant.setBackground(panelAntiguo.getBackground());

		JLabel titleMant = new JLabel("<html><u>Panel de mantenimiento</u></html>");
		titleMant.setHorizontalAlignment(SwingConstants.CENTER);
		titleMant.setFont(new Font("Times New Roman", Font.BOLD, 15));
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

					JButton bot = new JButton(tableName);
					bot.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							mostrarTabla(con, tableName);
							nombreTabla = tableName;
						}
					});
					bot.setAlignmentX(0.6f);
					panelMant.add(bot);
					panelMant.add(Box.createRigidArea(new Dimension(0, 20)));
				}
			}

			panelMant.revalidate();
			panelMant.repaint();

			JScrollPane jsc = new JScrollPane(panelMant);
			b.getPanel().remove(b.getPanelIzq());
			b.getPanel().add(jsc, BorderLayout.WEST);
			b.getPanel().revalidate();
			b.getPanel().repaint();

		} catch (ClassNotFoundException | SQLException e) {
		}
	}

	private void mostrarTabla(Connection c, String tNom) {
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
			model = new DefaultTableModel(datosArray, nombresColumnas);
			DefaultTableModel tOriginal = new DefaultTableModel(datosArray, nombresColumnas);

			tablaBase = new JTable(model);
			JScrollPane sc = new JScrollPane(tablaBase);

			pCent.add(sc, BorderLayout.CENTER);
			pCent.revalidate();
			pCent.repaint();

			model.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int fila = e.getFirstRow();
					int column = e.getColumn();
					if (e.getType() == TableModelEvent.UPDATE) {
						if (column != TableModelEvent.ALL_COLUMNS && fila != TableModelEvent.HEADER_ROW) {
							Object antiguoDato = tOriginal.getValueAt(fila, column);
							Object nuevoDato = model.getValueAt(fila, column);
							Object[] datosActualizacion = { tNom, nombresColumnas[column], nuevoDato, antiguoDato };
							Operaciones.actualizarTabla(c, datosActualizacion);
							sc.revalidate();
							sc.repaint();
						}
					}
				}
			});
		} catch (SQLException er) {
			System.out.println(er.getMessage());
		}
	}
	
	private void filtrarBusqueda() {
		//TODO
	}

}
