package paneles;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import inicio.VPrincipal;

public class Base extends JPanel {

	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	JLabel lblNewLabel = new JLabel();
	
	private VPrincipal v;

	/**
	 * Create the panel.
	 */
	public Base() {
		setLayout(null);
		
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 699, 435);
		add(panel);
		panel.setLayout(null);
		
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(168, 11, 327, 51);
		panel.add(lblNewLabel);

	}
	
	public void setV(VPrincipal v) {
		this.v=v;
		lblNewLabel.setText("Te has conectado a la base: "+v.getCon().getDatabaseName());
	}

}
