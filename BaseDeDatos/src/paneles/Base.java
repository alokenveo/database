package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import conexiones.Conexion;
import inicio.VPrincipal;

public class Base extends JPanel {

	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	JPanel panelIzq=new JPanel();
	JPanel centerPanel = new JPanel();
	JLabel lblNewLabel = new JLabel("¡Bienvenido a la base ");
	
	private VPrincipal v;
	private boolean botonesAdded=false;
	private Mantenimiento m;
	private Conexion c;

	/**
	 * Create the panel.
	 */
	public Base() {
		setLayout(null);
		
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 699, 435);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		//ZONA ARRIBA
		JPanel panelNorte = new JPanel();
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		panelNorte.add(Box.createRigidArea(new Dimension(200, 50)));
		panel.add(panelNorte, BorderLayout.NORTH);
		
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panelNorte.add(lblNewLabel);
		
		
		
		//ZONA IZQUIERDA
		panelIzq.setBackground(new Color(0, 128, 255));
        panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
        
        panelIzq.add(Box.createRigidArea(new Dimension(190, 20)));
        
        //Label Menú
        JLabel tituloMenu = new JLabel("<html><u>Menú de la base de datos</u></html>");
        tituloMenu.setHorizontalAlignment(SwingConstants.CENTER);
        tituloMenu.setFont(new Font("Times New Roman", Font.BOLD, 15));
        tituloMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIzq.add(tituloMenu);
        
        panelIzq.add(Box.createRigidArea(new Dimension(0, 20)));
        
        //Boton 1(Mantenimiento)
        JButton btnMant = new JButton("Mantenimiento");
        btnMant.setAlignmentX(0.6f);
        panelIzq.add(btnMant);
        
        panelIzq.add(Box.createRigidArea(new Dimension(0, 20)));
        
        //Boton 2 (Sentencias)
        
        panel.add(panelIzq,BorderLayout.WEST);
        
        JButton btnSentencias = new JButton("Sentencias SQL");
        btnSentencias.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(botonesAdded==false) {
        			addBotones(panelIzq);
        		}
        		else {
        			//Para ocultar los botones
        			int num=panelIzq.countComponents();
        			panelIzq.remove(num-1);
        			panelIzq.remove(num-2);
        			panelIzq.remove(num-3);
        			panelIzq.remove(num-4);
        			panelIzq.remove(num-5);
        			panelIzq.remove(num-6);
        			
        			botonesAdded=false;
        			
        			panelIzq.revalidate();
        			panelIzq.repaint();
        		}
        	}
        });
        btnSentencias.setAlignmentX(0.6f);
        panelIzq.add(btnSentencias);
        
        panelIzq.add(Box.createRigidArea(new Dimension(0, 15)));

        // ZONA CENTRAL
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel label = new JLabel("Panel central");
        centerPanel.add(label);
        panel.add(centerPanel,BorderLayout.CENTER);
        
        
        //Acción del boton de Mantenimiento
        btnMant.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		mostrarPanelMantenimiento(centerPanel);
        	}
        });

	}
	
	public void setV(VPrincipal v) {
		this.v=v;
		this.c=v.getCon();
		lblNewLabel.setText(lblNewLabel.getText()+v.getCon().getDatabaseName()+"!");
	}
	
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
	
	private void mostrarPanelMantenimiento(JPanel c) {
		panel.remove(c);
		m=new Mantenimiento();
		m.setBounds(c.getBounds());
		m.setB(this);
		panel.add(m);
		
		panel.revalidate();
		panel.repaint();
	}
	
	private void addBotones(JPanel panelIzq) {
		JButton bt1=new JButton("Acción 1");
		JButton bt2=new JButton("Acción 2");
		JButton bt3=new JButton("Acción 3");
		
		bt1.setAlignmentX(0.5f);
		bt2.setAlignmentX(0.5f);
		bt3.setAlignmentX(0.5f);
		
		panelIzq.add(bt1);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 10)));
		
		panelIzq.add(bt2);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 10)));
		
		panelIzq.add(bt3);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 10)));
		
		panelIzq.revalidate();
		panelIzq.repaint();
		
		botonesAdded=true;
	}

}