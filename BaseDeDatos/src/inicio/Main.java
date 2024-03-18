package inicio;

import java.awt.EventQueue;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("H");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPrincipal frame = new VPrincipal();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setTitle("Conexión a base de datos");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
