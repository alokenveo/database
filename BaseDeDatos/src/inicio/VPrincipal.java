package inicio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import conexiones.Conexion;
import paneles.Base;
import java.awt.FlowLayout;

public class VPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	// Componentes de la ventana
	JPanel contentPane;
	JPanel pPrincipal;
	JPanel panel;
	JLabel etInicio;
	JLabel lblNewLabel;
	JTextField dbName;
	JButton btConectar;
	JLabel etAviso;

	// Atributos
	private Base p;
	private Conexion con;

	/**
	 * Create the frame.
	 */
	// CONSTRUCTOR DE LA VENTANA
	public VPrincipal() {
		// Inicializaciones
		contentPane = new JPanel();
		pPrincipal = new JPanel();
		panel = new JPanel();
		etInicio = new JLabel("INICIAR BASE DE DATOS");
		lblNewLabel = new JLabel("Nombre de la base de datos");
		dbName = new JTextField();
		btConectar = new JButton("Conectar");
		etAviso = new JLabel("");

		// Parámetros de la ventana inicial
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(240, 70, 870, 600);
		setSize(870,600);
		setLocationRelativeTo(null);
		setTitle("Conexión a base de datos");
		setContentPane(contentPane);

		// Parámetros del Panel ContentPane
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		// Parámtero del Panel pPrincipal
		pPrincipal.setBackground(new Color(0, 128, 255));
		pPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Parámetros de panel
		panel.setPreferredSize(new Dimension(243, 290));
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(null);

		// Parámetros del Label de presentación
		etInicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		etInicio.setHorizontalAlignment(SwingConstants.CENTER);
		etInicio.setBounds(20, 11, 207, 26);

		// Esto es donde se escribe el nombre de la base de datos
		dbName.setBounds(31, 136, 185, 26);
		dbName.setColumns(10);

		// Parámetros del label de aviso
		etAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		etAviso.setForeground(Color.RED);
		etAviso.setHorizontalAlignment(SwingConstants.CENTER);
		etAviso.setBounds(34, 254, 193, 23);

		//Parámetro del label de info
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(45, 99, 161, 26);
		
		//Parámetros del botón conectar
		btConectar.setBounds(83, 202, 89, 23);

		/*
		 * ACCIONES
		 */
		//Acción del bóton Conectar
		btConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (conectarBase(dbName.getText()) && !dbName.getText().isEmpty()) {
						p = new Base();
						contentPane.removeAll();

						p.setV(VPrincipal.this);

						contentPane.add(p);
						contentPane.revalidate();
						contentPane.repaint();

					} else {
						mostrarMensaje("Error. No es posible conectarse");
					}
				} catch (ClassNotFoundException | SQLException e1) {
				}
			}
		});
		
		//Acción sobre el text field
		dbName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btConectar.doClick();
			}
		});

		// Lo añadimos a su contenedor
		panel.add(etInicio);
		panel.add(etAviso);
		panel.add(lblNewLabel);
		panel.add(dbName);		
		panel.add(btConectar);
		pPrincipal.add(Box.createRigidArea(new Dimension(0, 650)));
		pPrincipal.add(panel);
		contentPane.add(pPrincipal);
	}

	//GETTERS
	public Base getP() {
		return p;
	}

	public Conexion getCon() {
		return con;
	}
	
	//MÉTODOS PRIVADOS
	//Método para hacer la conexión
	private boolean conectarBase(String dbName) throws ClassNotFoundException, SQLException {
		con = new Conexion(dbName);
		Connection cn = con.getConnection();
		if (cn != null) {
			return true;
		} else {

			return false;
		}
	}

	//Método para mostrar el mensaje de aviso
	private void mostrarMensaje(String mensaje) {
		etAviso.setText(mensaje);

		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				etAviso.setText("");
				((Timer) e.getSource()).stop();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
}
