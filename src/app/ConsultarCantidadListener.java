package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Producto;

public class ConsultarCantidadListener implements ActionListener {
    private final JTextArea areaProductos;

    public ConsultarCantidadListener(JTextArea areaProductos) {
        this.areaProductos = areaProductos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int cantidad = App.catalogo.listarProductos().size();
        areaProductos.append("\n╔═══════════════════════════════════════════════╗\n");
        areaProductos.append("║                RESUMEN DEL CATÁLOGO            ║\n");
        areaProductos.append("╠═══════════════════════════════════════════════╣\n");
        areaProductos.append("Cantidad total de productos: " + cantidad + "\n");
        areaProductos.append("─────────────────────────────────────────────────\n");
        areaProductos.append("LISTA COMPLETA DE PRODUCTOS:\n");
        areaProductos.append("─────────────────────────────────────────────────\n");

        if (cantidad == 0) {
            areaProductos.append("El catálogo está vacío.\n");
            areaProductos.append("Agregue productos para comenzar.\n");
        } else {
            for (int i = 0; i < App.catalogo.listarProductos().size(); i++) {
                Producto p = App.catalogo.listarProductos().get(i);
                areaProductos.append((i + 1) + ". " + p + "\n");
            }
        }
        areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
    }
}