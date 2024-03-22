package panelesPruebas;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ScrollableButtonPanel extends JFrame {
    public ScrollableButtonPanel() {
        setTitle("Panel de Botones Desplazable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Crear un panel para contener los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1)); // GridLayout con una sola columna

        // Agregar varios botones al panel
        for (int i = 1; i <= 20; i++) {
            JButton button = new JButton("Botón " + i);
            buttonPanel.add(button);
        }

        // Crear un JScrollPane y agregar el panel de botones a él
        JScrollPane scrollPane = new JScrollPane(buttonPanel);

        // Configurar el JScrollPane para desplazamiento vertical automático
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Agregar el JScrollPane al JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScrollableButtonPanel frame = new ScrollableButtonPanel();
            frame.setVisible(true);
        });
    }
}
