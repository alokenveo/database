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
	private JPanel contentPane;
	private Base p;
	private JTextField dbName;
	JLabel etAviso = new JLabel("");
	
	private Conexion con;

	/**
	 * Create the frame.
	 */
	public VPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(240, 70, 870, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pPrincipal = new JPanel();
		pPrincipal.setBackground(new Color(0, 128, 255));
		contentPane.add(pPrincipal);
		pPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(243,290));
		panel.setBackground(new Color(255, 255, 255));
		pPrincipal.add(panel);
		panel.setLayout(null);
		
		pPrincipal.add(Box.createRigidArea(new Dimension(0,50)));
		
		JLabel etInicio = new JLabel("INICIAR BASE DE DATOS");
		etInicio.setFont(new Font("Tahoma", Font.BOLD, 15));
		etInicio.setHorizontalAlignment(SwingConstants.CENTER);
		etInicio.setBounds(20, 11, 207, 26);
		panel.add(etInicio);
		
		etAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		etAviso.setForeground(Color.RED);
		etAviso.setHorizontalAlignment(SwingConstants.CENTER);
		etAviso.setBounds(34, 254, 193, 23);
		panel.add(etAviso);
		
		
		//Esto es donde se escribe el nombre de la base de datos
		dbName = new JTextField();
		dbName.setToolTipText("");
		dbName.setBounds(31, 136, 185, 26);
		panel.add(dbName);
		dbName.setColumns(10);
		
		
		JButton btConectar = new JButton("Conectar");
		btConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(conectarBase(dbName.getText()) && !dbName.getText().isEmpty()) {
					p=new Base();
					contentPane.removeAll();
					p.setBounds(0, 0, 699, 435);
					
					p.setV(VPrincipal.this);
					
					contentPane.add(p);
					contentPane.revalidate();
					contentPane.repaint();
					
					}
					else {
						mostrarMensaje("Error. No es posible conectarse");
					}
				} catch (ClassNotFoundException | SQLException e1) {
				}
			}
		});
		btConectar.setBounds(83, 202, 89, 23);
		panel.add(btConectar);
		
		JLabel lblNewLabel = new JLabel("Nombre de la base de datos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(45, 99, 161, 26);
		panel.add(lblNewLabel);
		
		dbName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btConectar.doClick();
            }
        });
	}

	private boolean conectarBase(String dbName) throws ClassNotFoundException, SQLException {
		con=new Conexion(dbName);
		
		Connection cn=con.getConnection();
		if (cn != null) {
			return true;
		} else {
			
			return false;
		}
	}
	
	public Base getP() {
		return p;
	}
	
	public Conexion getCon() {
		return con;
	}
	
	private void mostrarMensaje(String mensaje) {
        etAviso.setText(mensaje);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                etAviso.setText("");
                ((Timer)e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
