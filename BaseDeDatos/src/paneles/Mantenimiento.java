package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Mantenimiento extends JPanel {

	private static final long serialVersionUID = 1L;

	private Base b;
	private JPanel panelAntiguo;
	private JPanel panelMant;

	/**
	 * Create the panel.
	 */
	public Mantenimiento() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Estás haciendo el mantenimiento de la base de datos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(51, 128, 430, 82);
		panel.add(lblNewLabel);

	}

	public void setB(Base b) {
		this.b = b;
		this.panelAntiguo = b.getPanelIzq();
		nuevoPanel();
	}

	private void nuevoPanel() {
		// Creo el nuevo JPanel
		panelMant = new JPanel();
		panelMant.setLayout(new BoxLayout(panelMant, BoxLayout.Y_AXIS));
		panelMant.add(Box.createRigidArea(new Dimension(190, 20)));
		panelMant.setBackground(panelAntiguo.getBackground());
		
		JLabel titleMant=new JLabel("<html><u>Panel de mantenimiento</u></html>");
		titleMant.setHorizontalAlignment(SwingConstants.CENTER);
        titleMant.setFont(new Font("Times New Roman", Font.BOLD, 15));
        titleMant.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelMant.add(titleMant);

		// Quito el antiugo Jpanel;
		b.getPanel().remove(panelAntiguo);
		b.getPanel().add(panelMant, BorderLayout.WEST);

		b.getPanel().revalidate();
		b.getPanel().repaint();
	}

}
