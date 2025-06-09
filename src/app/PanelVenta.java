package app;

import javax.swing.*;
import java.awt.Font;
import java.awt.Dimension;

public class PanelVenta extends JPanel {
    public PanelVenta(JFrame frame, JTextArea areaProductos) {
        setLayout(new java.awt.GridLayout(6, 2, 10, 10));
        setBackground(App.COLOR_FONDO_SECUNDARIO);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(App.COLOR_ACENTO, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                "Registrar Venta",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                App.COLOR_TEXTO
            )
        ));
        setPreferredSize(new Dimension(300, 0));

        JTextField tfCodigoVenta = UtilidadesSwing.crearTextField();
        JTextField tfCantidadVenta = UtilidadesSwing.crearTextField();
        String[] medios = {"Efectivo", "Débito", "Crédito (2 cuotas)", "Crédito (3 cuotas)", "Crédito (6 cuotas)"};
        JComboBox<String> cbMedioPago = UtilidadesSwing.crearComboBox(medios);

        JButton btnRegistrarVenta = UtilidadesSwing.crearBotonModerno("Registrar Venta", App.COLOR_EXITO);
        btnRegistrarVenta.setPreferredSize(new Dimension(120, 35));

        add(UtilidadesSwing.crearLabel("Código producto:")); add(tfCodigoVenta);
        add(UtilidadesSwing.crearLabel("Cantidad:")); add(tfCantidadVenta);
        add(UtilidadesSwing.crearLabel("Medio de pago:")); add(cbMedioPago);

        JPanel espaciador = new JPanel();
        espaciador.setBackground(App.COLOR_FONDO_SECUNDARIO);
        add(espaciador); add(btnRegistrarVenta);

        JTextArea infoVenta = new JTextArea(3, 20);
        infoVenta.setEditable(false);
        infoVenta.setBackground(App.COLOR_FONDO_SECUNDARIO);
        infoVenta.setForeground(App.COLOR_TEXTO);
        infoVenta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        infoVenta.setLineWrap(true);
        infoVenta.setWrapStyleWord(true);
        infoVenta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(App.COLOR_ADVERTENCIA, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        add(new JLabel()); add(infoVenta);

        // Actualizar infoVenta según el medio de pago seleccionado
        cbMedioPago.addActionListener(e -> {
            String medio = (String) cbMedioPago.getSelectedItem();
            String texto = "";
            java.awt.Color colorBorde = App.COLOR_ADVERTENCIA;

            switch (medio) {
                case "Efectivo":
                    texto = "DESCUENTO: 10%";
                    colorBorde = App.COLOR_EXITO;
                    break;
                case "Débito":
                    texto = "SIN RECARGO";
                    colorBorde = App.COLOR_ACENTO;
                    break;
                case "Crédito (2 cuotas)":
                    texto = "RECARGO: 6%";
                    colorBorde = App.COLOR_ADVERTENCIA;
                    break;
                case "Crédito (3 cuotas)":
                    texto = "RECARGO: 12%";
                    colorBorde = App.COLOR_ADVERTENCIA;
                    break;
                case "Crédito (6 cuotas)":
                    texto = "RECARGO: 20%";
                    colorBorde = App.COLOR_PELIGRO;
                    break;
            }
            infoVenta.setText(texto);
            infoVenta.setForeground(App.COLOR_TEXTO);
            infoVenta.setBackground(App.COLOR_FONDO_SECUNDARIO);
            infoVenta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
        });

        cbMedioPago.setSelectedIndex(0);
        cbMedioPago.getActionListeners()[0].actionPerformed(null);

        // Ajusta el constructor del listener (sin infoVenta)
        btnRegistrarVenta.addActionListener(
            new RegistrarVentaListener(tfCodigoVenta, tfCantidadVenta, cbMedioPago, areaProductos, frame)
        );
    }
}