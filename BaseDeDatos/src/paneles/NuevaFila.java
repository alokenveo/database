package paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class NuevaFila extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contenido = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NuevaFila dialog = new NuevaFila();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NuevaFila() {
		setTitle("Nueva Fila");
		setBounds(550, 130, 310, 381);
		getContentPane().setLayout(new BorderLayout());
		contenido.setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 0, 0)));
		getContentPane().add(contenido, BorderLayout.CENTER);
		contenido.setLayout(null);
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
						dispose();
						System.out.println("Ventana cerrada");
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
		pArriba.add(Box.createRigidArea(new Dimension(0,45)));
		
		JLabel lab=new JLabel("<html><u>Añadir nueva fila</u></html>");
		lab.setAlignmentX(Component.CENTER_ALIGNMENT);
		lab.setFont(new Font("Tahoma", Font.BOLD, 15));
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		pArriba.add(lab);
	}
}
