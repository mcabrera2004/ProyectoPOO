package app;

import javax.swing.*;
import java.awt.*;
import model.CatalogoProductos;

public class App {
    public static final Color COLOR_FONDO_PRINCIPAL = new Color(18, 18, 18);
    public static final Color COLOR_FONDO_SECUNDARIO = new Color(30, 30, 30);
    public static final Color COLOR_ACENTO = new Color(0, 150, 255);
    public static final Color COLOR_ACENTO_HOVER = new Color(0, 120, 200);
    public static final Color COLOR_TEXTO = new Color(255, 255, 255);
    public static final Color COLOR_EXITO = new Color(46, 204, 113);
    public static final Color COLOR_PELIGRO = new Color(231, 76, 60);
    public static final Color COLOR_ADVERTENCIA = new Color(241, 196, 15);
    public static final Color COLOR_NARANJA = new Color(255, 143, 0);

    public static CatalogoProductos catalogo = new CatalogoProductos();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(App::crearVentana);
    }

    public static void crearVentana() {
        JFrame frame = new JFrame("Sistema de Gestión de Productos v2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(COLOR_FONDO_PRINCIPAL);
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        PanelListaProductos panelLista = new PanelListaProductos();

        PanelIngresoProductos panelIngreso = new PanelIngresoProductos(frame, panelLista.getAreaProductos());

        PanelVenta panelVenta = new PanelVenta(frame, panelLista.getAreaProductos());

        frame.add(panelIngreso, BorderLayout.NORTH);
        frame.add(panelLista, BorderLayout.CENTER);
        frame.add(panelVenta, BorderLayout.EAST);
        frame.add(panelLista.getBotonStockMinimo(), BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}