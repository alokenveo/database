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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import conexiones.Conexion;

public class Mantenimiento extends JPanel {

	private static final long serialVersionUID = 1L;

	private Base b;
	private JPanel panelAntiguo;
	private JPanel panelMant;
	private Conexion c;

	JLabel titleTabla = new JLabel("Tabla");
	JPanel pCent = new JPanel();

	/**
	 * Create the panel.
	 */
	public Mantenimiento() {
		setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, 654, 501);

		// Panel principal
		JPanel pMant = new JPanel();
		pMant.setBackground(new Color(255, 255, 255));
		pMant.setBounds(0, 0, 654, 501);
		add(pMant);
		pMant.setLayout(new BorderLayout(0, 0));

		// Panel de arriba
		JPanel pInfoMant = new JPanel();
		pMant.add(pInfoMant, BorderLayout.NORTH);
		pInfoMant.setLayout(new BoxLayout(pInfoMant, BoxLayout.X_AXIS));

		Component rigidArea = Box.createRigidArea(new Dimension(230, 30));
		pInfoMant.add(rigidArea);
		titleTabla.setHorizontalAlignment(SwingConstants.CENTER);
		titleTabla.setFont(new Font("Tahoma", Font.BOLD, 13));

		titleTabla.setPreferredSize(new Dimension(135, 30));
		pInfoMant.add(titleTabla);

		pInfoMant.add(Box.createRigidArea(new Dimension(250, 30)));

		ImageIcon iconoOriginal = new ImageIcon("src/recursos/flecha_atras.png");
		Image scaledImage = iconoOriginal.getImage().getScaledInstance(15, 17, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaledImage);
		JButton btnAtras = new JButton(icon);

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
		JPanel pDer = new JPanel();
		pDer.setLayout(new BoxLayout(pDer, BoxLayout.Y_AXIS));
		pDer.add(Box.createRigidArea(new Dimension(150, 20)));
		JLabel titleDer = new JLabel("Panel derecho");
		titleDer.setAlignmentX(CENTER_ALIGNMENT);
		pDer.add(titleDer);
		pMant.add(pDer, BorderLayout.EAST);

		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}

	public void setB(Base b) {
		this.b = b;
		this.panelAntiguo = b.getPanelIzq();
		this.c = b.getCon();
		nuevoPanel();
	}

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
			DatabaseMetaData metaData = con.getMetaData();
			String catalog = con.getCatalog();
			ResultSet resultSet = metaData.getTables(catalog, null, "%", new String[] { "TABLE" });

			int count = 0;
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				if (!tableName.startsWith("trace_xe")) {
					// System.out.println("Tabla encontrada: " + tableName);

					JButton bot = new JButton(tableName);
					bot.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO
							titleTabla.setText(tableName);
							pCent.removeAll();
							try {
								Statement statement = con.createStatement();
								ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

								int numCol = resultSet.getMetaData().getColumnCount();

								List<Object[]> datos = new ArrayList<>();

								while (resultSet.next()) {
									Object[] filaDatos = new Object[numCol];
									for (int columna = 1; columna <= numCol; columna++) {
										filaDatos[columna - 1] = resultSet.getObject(columna);
									}
									datos.add(filaDatos);
								}

								Object[][] datosArray = new Object[datos.size()][numCol];
								for (int i = 0; i < datos.size(); i++) {
									datosArray[i] = datos.get(i);
								}

								String[] nombresColumnas = new String[numCol];
								for (int i = 0; i < nombresColumnas.length; i++) {
									nombresColumnas[i] = "Columna " + (i + 1);
								}

								DefaultTableModel model = new DefaultTableModel(datosArray, nombresColumnas);
								JTable tablaBase = new JTable(model);
								tablaBase.setLayout(pCent.getLayout());

								pCent.add(tablaBase, BorderLayout.CENTER);
								pCent.revalidate();
								pCent.repaint();
							} catch (SQLException er) {
								System.out.println(er.getMessage());
							}
						}
					});
					bot.setAlignmentX(0.6f);
					panelMant.add(bot);
					panelMant.add(Box.createRigidArea(new Dimension(0, 20)));

					count++;
				}
			}
			panelMant.revalidate();
			panelMant.repaint();

			// System.out.println("Número total de tablas: " + count);
		} catch (ClassNotFoundException | SQLException e) {
		}

	}

}
