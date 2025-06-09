package app;

import javax.swing.*;
import java.awt.*;

public class PanelListaProductos extends JPanel {
    private final JTextArea areaProductos;
    private final JButton btnMostrarStockMin;

    public PanelListaProductos() {
        setLayout(new BorderLayout());
        areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        areaProductos.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        areaProductos.setBackground(App.COLOR_FONDO_SECUNDARIO);
        areaProductos.setForeground(App.COLOR_TEXTO);
        areaProductos.setCaretColor(App.COLOR_ACENTO);
        areaProductos.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaProductos);
        scroll.setBackground(App.COLOR_FONDO_SECUNDARIO);
        scroll.getViewport().setBackground(App.COLOR_FONDO_SECUNDARIO);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(App.COLOR_ACENTO, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                "Lista de Productos",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                App.COLOR_TEXTO
            )
        ));

        add(scroll, BorderLayout.CENTER);

        btnMostrarStockMin = UtilidadesSwing.crearBotonModerno("Mostrar Stock Mínimo", App.COLOR_PELIGRO);
        // PASA areaProductos al listener
        btnMostrarStockMin.addActionListener(new MostrarStockMinimoListener(areaProductos));
    }

    public JTextArea getAreaProductos() {
        return areaProductos;
    }

    public JButton getBotonStockMinimo() {
        return btnMostrarStockMin;
    }
}