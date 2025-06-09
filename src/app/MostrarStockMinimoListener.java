package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Producto;

public class MostrarStockMinimoListener implements ActionListener {
    private final JTextArea areaProductos;

    public MostrarStockMinimoListener(JTextArea areaProductos) {
        this.areaProductos = areaProductos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        areaProductos.append("\n╔═══════════════════════════════════════════════╗\n");
        areaProductos.append("║        PRODUCTOS CON STOCK MÍNIMO ALCANZADO   ║\n");
        areaProductos.append("╠═══════════════════════════════════════════════╣\n");

        List<Producto> productosStockMin = App.catalogo.marcarStockMinimo();

        if (productosStockMin.isEmpty()) {
            areaProductos.append("No hay productos con stock mínimo.\n");
            areaProductos.append("Todos los productos tienen stock suficiente.\n");
        } else {
            areaProductos.append("Se encontraron " + productosStockMin.size() + " producto(s):\n\n");
            for (Producto p : productosStockMin) {
                areaProductos.append("⚠️  " + p + "\n");
            }
        }
        areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
    }
}