package panelesPruebas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Pruebas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de JTable Persistente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Datos iniciales para el modelo de tabla
        Vector<Vector<Object>> data = new Vector<>();
        Vector<Object> row1 = new Vector<>();
        row1.add("John");
        row1.add(25);
        data.add(row1);
        Vector<Object> row2 = new Vector<>();
        row2.add("Jane");
        row2.add(30);
        data.add(row2);
        Vector<Object> row3 = new Vector<>();
        row3.add("Doe");
        row3.add(40);
        data.add(row3);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Nombre");
        columnNames.add("Edad");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int fila = e.getFirstRow();
                int column = e.getColumn();
                if (column != TableModelEvent.ALL_COLUMNS && fila != TableModelEvent.HEADER_ROW) {
                    Object nuevoDato = model.getValueAt(fila, column);
                    System.out.println("Nuevo valor en fila " + fila + ", columna " + column + ": " + nuevoDato);
                }
            }
        });
        JTable table = new JTable(model);

        // Agregar un WindowListener para imprimir los datos cuando se cierre la ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Imprimir los datos de la tabla
                System.out.println("Datos de la tabla:");
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        System.out.print(model.getValueAt(i, j) + "\t");
                    }
                    System.out.println();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }
}
