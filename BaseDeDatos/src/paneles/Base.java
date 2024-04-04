package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import conexiones.Conexion;
import conexiones.Operaciones;
import inicio.VPrincipal;

public class Base extends JPanel {

	private static final long serialVersionUID = 1L;

	// Componentes de la ventana
	JPanel panel;
	JPanel panelNorte;
	JLabel lblNewLabel;
	JPanel panelIzq;
	JLabel tituloMenu;
	JButton btnMant;
	JButton btnSentencias;
	JPanel centerPanel;
	JLabel lblSeHan;

	// Atributos
	private VPrincipal v;
	private boolean botonesAdded = false;
	private Mantenimiento m;
	private Conexion c;

	private JTextPane desc_sentencia;

	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public Base() {
		// Inicializaciones
		panel = new JPanel();
		panelNorte = new JPanel();
		lblNewLabel = new JLabel("¡Bienvenido a la base ");
		panelIzq = new JPanel();
		tituloMenu = new JLabel("<html><u>Menú de la base de datos</u></html>");
		btnMant = new JButton("Mantenimiento");
		btnSentencias = new JButton("Sentencias SQL");
		centerPanel = new JPanel();
		lblSeHan = new JLabel("<html><u>Visualización de sentencias</u></html>");
		lblSeHan.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeHan.setBounds(198, 5, 202, 19);

		// Parámteros de la ventana inicial
		setBounds(0, 0, 844, 551);
		setLayout(null);

		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 844, 551);
		panel.setLayout(new BorderLayout(0, 0));

		// ZONA ARRIBA
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		panelNorte.add(Box.createRigidArea(new Dimension(295, 50)));
		panel.add(panelNorte, BorderLayout.NORTH);

		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panelNorte.add(lblNewLabel);

		// ZONA IZQUIERDA
		panelIzq.setBackground(new Color(0, 128, 255));
		panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
		panelIzq.add(Box.createRigidArea(new Dimension(190, 20)));

		// Label Menú
		tituloMenu.setHorizontalAlignment(SwingConstants.CENTER);
		tituloMenu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tituloMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelIzq.add(tituloMenu);

		panelIzq.add(Box.createRigidArea(new Dimension(0, 20)));

		// Boton 1(Mantenimiento)
		btnMant.setAlignmentX(0.6f);
		panelIzq.add(btnMant);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 20)));

		// Boton 2 (Sentencias)
		btnSentencias.setAlignmentX(0.6f);
		panelIzq.add(btnSentencias);

		panelIzq.add(Box.createRigidArea(new Dimension(0, 15)));

		// ZONA CENTRAL
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(null);

		lblSeHan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		centerPanel.add(lblSeHan);
		panel.add(centerPanel, BorderLayout.CENTER);

		desc_sentencia = new JTextPane();
		desc_sentencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		desc_sentencia.setBounds(61, 58, 530, 81);
		centerPanel.add(desc_sentencia);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(135, 159, 393, 306);
		centerPanel.add(scrollPane);

		JButton btnLimpiar = new JButton("LIMPIAR");
		btnLimpiar.setBounds(555, 5, 89, 23);
		centerPanel.add(btnLimpiar);

		/*
		 * ACCIONES
		 */
		// Acción del boton de Mantenimiento
		btnMant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanelMantenimiento(centerPanel);
			}
		});

		// Acción del boton de sentencias
		btnSentencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (botonesAdded == false) {
					addBotones(panelIzq);
				} else {
					// Para ocultar los botones
					@SuppressWarnings("deprecation")
					int num = panelIzq.countComponents();
					panelIzq.remove(num - 1);
					panelIzq.remove(num - 2);
					panelIzq.remove(num - 3);
					panelIzq.remove(num - 4);
					panelIzq.remove(num - 5);
					panelIzq.remove(num - 6);

					botonesAdded = false;

					panelIzq.revalidate();
					panelIzq.repaint();
				}
			}
		});

		// Acción del botón Limpiar
		btnLimpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desc_sentencia.setText("");
				scrollPane.setVisible(false);
			}
		});

		// Añdimos los componentes a sus contedores
		panel.add(panelIzq, BorderLayout.WEST);
		add(panel);
	}

	// GETTERS Y SETTERS
	public JPanel getPanelIzq() {
		return panelIzq;
	}

	public JPanel getPanel() {
		return panel;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public Conexion getCon() {
		return c;
	}

	public VPrincipal getV() {
		return v;
	}

	public void setV(VPrincipal v) {
		this.v = v;
		this.c = v.getCon();
		lblNewLabel.setText(lblNewLabel.getText() + v.getCon().getDatabaseName() + "!");
	}

	// MÉTODOS PRIVADOS
	private void mostrarPanelMantenimiento(JPanel c) {
		panel.remove(c);
		m = new Mantenimiento();
		m.setBounds(c.getBounds());
		m.setB(this);
		panel.add(m);

		panel.revalidate();
		panel.repaint();
	}

	// Método para mostrar las acciones predeterminadas
	private void addBotones(JPanel panelIzq) {
		JButton bt1 = new JButton("Acción 1");
		JButton bt2 = new JButton("Acción 2");
		JButton bt3 = new JButton("Acción 3");

		bt1.setAlignmentX(0.5f);
		bt2.setAlignmentX(0.5f);
		bt3.setAlignmentX(0.5f);

		// Acciones de los 3 botones
		bt1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sentencia = "SELECT \r\n" + "    Herramientas.her_descripcion AS 'Descripción Herramienta',\r\n"
						+ "    Reparaciones.rep_nombre AS 'Nombre Reparación',\r\n"
						+ "    Locomotora.loc_nombre AS 'Nombre Locomotora'\r\n" + "FROM \r\n" + "    Herramientas \r\n"
						+ "JOIN \r\n"
						+ "    Emplean  ON Herramientas.her_cod_herramienta = Emplean.emp_cod_herramienta\r\n"
						+ "JOIN \r\n" + "    Tareas  ON Emplean.emp_cod_reparacion = Tareas.tar_cod_reparacion\r\n"
						+ "JOIN \r\n"
						+ "    Reparaciones ON Tareas.tar_cod_reparacion = Reparaciones.rep_cod_reparacion\r\n"
						+ "JOIN \r\n"
						+ "    Locomotora  ON Reparaciones.rep_cod_locomotora = Locomotora.Loc_cod_locomotora\r\n"
						+ "WHERE \r\n" + "    Tareas.tar_nombre = 'Cambio de Válvulas'\r\n"
						+ "    AND Locomotora.loc_fecha_inicio >= '01/01/2008'\r\n"
						+ "    AND Locomotora.loc_fecha_inicio <= '31/05/2009'\r\n" + "ORDER BY \r\n"
						+ "    Locomotora.loc_nombre ASC;";
				DefaultTableModel model = new DefaultTableModel();
				try {
					if (Operaciones.ejecutarSentencia(c.getConnection(), sentencia, model, desc_sentencia)) {
						desc_sentencia.setText(
								"Esta sentencia se encarga de seleccionar todas las herramientas empleadas en la tarea “Cambio de Válvulas” entre el primer día de Enero "
										+ "de 2008 y el último día de Mayo de 2009, solo de aquellas reparaciones cuyas locomotoras hayan empezado "
										+ "a funcionar después del año 2000, ordenada ascendentemente por nombre de locomotora");
						JTable tab = new JTable(model);
						scrollPane.setViewportView(tab);
						scrollPane.setVisible(true);
					} else {
						scrollPane.setVisible(false);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					desc_sentencia.setText(e1.getMessage());
				}
			}
		});
		bt2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sentencia = "SELECT \r\n" + "    ope.ope_nombre AS [Nombre Aprendiz],\r\n"
						+ "    t.tar_nombre AS [Nombre Tarea],\r\n" + "    t.tar_duracion AS [Duración Tarea],\r\n"
						+ "    COUNT(*) AS [Total tareas realizadas],\r\n"
						+ "    SUM(t.tar_duracion) AS [Horas totales realizadas]\r\n" + "FROM \r\n"
						+ "    Operarios ope\r\n" + "JOIN \r\n" + "    Asisten ast ON ope.ope_dni = ast.Ast_dni\r\n"
						+ "JOIN \r\n" + "    Asigna asi ON ast.Ast_cod_curso = asi.Asi_cod_reparacion\r\n" + "JOIN \r\n"
						+ "    Tareas t ON asi.Asi_num_tarea = t.tar_num_tarea AND asi.Asi_cod_reparacion = t.tar_cod_reparacion\r\n"
						+ "JOIN \r\n" + "    Areas_trabajo are ON asi.Asi_num_area = are.are_num_area\r\n"
						+ "WHERE \r\n" + "    ope.ope_tipo = 'A' -- Operarios Aprendices\r\n"
						+ "    AND are.are_localizacion = 'Motores Diesel'\r\n" + "GROUP BY \r\n"
						+ "    ope.ope_nombre,\r\n" + "    t.tar_nombre,\r\n" + "    t.tar_duracion\r\n"
						+ "ORDER BY \r\n" + "    ope.ope_nombre ASC,\r\n" + "    t.tar_nombre ASC;";
				DefaultTableModel model = new DefaultTableModel();
				try {
					if (Operaciones.ejecutarSentencia(c.getConnection(), sentencia, model, desc_sentencia)) {
						desc_sentencia.setText(
								"Esta sentencia se encarga de seleccionar la cantidad total de tareas y el número total de horas empleadas en ellas que han sido realizadas "
										+ "por los operarios Aprendices en el área de localización llamada “Motores Diesel”, ordenadas "
										+ "ascendentemente por el nombre del Aprendiz y el nombre de la tarea");
						JTable tab = new JTable(model);
						scrollPane.setViewportView(tab);
						scrollPane.setVisible(true);
					} else {
						scrollPane.setVisible(false);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					desc_sentencia.setText(e1.getMessage());
				}
			}
		});
		bt3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sentencia = "SELECT \r\n" + "    ope.ope_nombre AS [Nombre Aprendiz],\r\n"
						+ "    YEAR(punt_fecha) AS [Año Realización],\r\n"
						+ "    AVG(pun_puntuacion) AS [Puntuación Media]\r\n" + "FROM \r\n" + "    Operarios ope\r\n"
						+ "JOIN \r\n" + "    Asisten ast ON ope.ope_dni = ast.Ast_dni\r\n" + "JOIN \r\n"
						+ "    Cursos cur ON ast.Ast_cod_curso = cur.cur_cod_curso\r\n" + "JOIN \r\n"
						+ "    Puntuacion pun ON ast.Ast_cod_curso = pun.pun_cod_curso AND ast.Ast_dni = pun.pun_dni\r\n"
						+ "WHERE \r\n" + "    ope.ope_tipo = 'A' -- Operarios Aprendices\r\n"
						+ "    AND cur.cur_nombre = 'Reglajes de Válvulas'\r\n"
						+ "    AND YEAR(punt_fecha) BETWEEN YEAR(GETDATE()) - 2 AND YEAR(GETDATE()) - 1\r\n"
						+ "GROUP BY \r\n" + "    ope.ope_nombre,\r\n" + "    YEAR(punt_fecha)\r\n" + "HAVING \r\n"
						+ "    AVG(pun_puntuacion) > 7\r\n" + "ORDER BY \r\n" + "    [Puntuación Media] DESC,\r\n"
						+ "    [Año Realización] DESC;";
				DefaultTableModel model = new DefaultTableModel();
				try {
					if (Operaciones.ejecutarSentencia(c.getConnection(), sentencia, model, desc_sentencia)) {
						desc_sentencia.setText(
								"Esta sentencia se encarga de obtener un listado con los operarios aprendices que han obtenido una media mayor de siete (7) puntos en "
										+ "los cursos llamados “Reglajes de Válvulas” realizados durante los dos (2) años anteriores al año en curso, "
										+ "ordenado descendentemente por la puntuación media y año");
						JTable tab = new JTable(model);
						scrollPane.setViewportView(tab);
						scrollPane.setVisible(true);
					} else {
						scrollPane.setVisible(false);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					desc_sentencia.setText(e1.getMessage());
				}
			}
		});

		panelIzq.add(bt1);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 10)));

		panelIzq.add(bt2);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 10)));

		panelIzq.add(bt3);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 10)));

		panelIzq.revalidate();
		panelIzq.repaint();

		botonesAdded = true;
	}
}
