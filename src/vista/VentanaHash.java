package vista;

import controlador.ControladorHash;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaHash extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaHash() {

        setTitle("Tabla Hash");
        setSize(400, 500);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel();

        modelo.addColumn("Dato");
        modelo.addColumn("Índice Hash");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        JButton generar = new JButton("Generar Hash");

        add(generar, BorderLayout.SOUTH);

        modelo.addRow(new Object[]{"", ""});
        modelo.addRow(new Object[]{"", ""});
        modelo.addRow(new Object[]{"", ""});

        generar.addActionListener(e -> generarHash());
    }

    private void generarHash() {

        ControladorHash controlador = new ControladorHash();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            Object dato = modelo.getValueAt(i, 0);

            if (dato != null) {

                String texto = dato.toString();

                if (!texto.isEmpty()) {

                    int indice = controlador.insertarDato(texto);

                    modelo.setValueAt(indice, i, 1);
                }
            }
        }
    }
}