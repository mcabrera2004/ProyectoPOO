package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import model.Producto;

public class BuscarProductoListener implements ActionListener {
    private final JTextField tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin;
    private final JTextArea areaProductos;

    public BuscarProductoListener(JTextField tfCodigo, JTextField tfDescripcion, JTextField tfPrecio, JTextField tfStock, JTextField tfStockMin, JTextArea areaProductos) {
        this.tfCodigo = tfCodigo;
        this.tfDescripcion = tfDescripcion;
        this.tfPrecio = tfPrecio;
        this.tfStock = tfStock;
        this.tfStockMin = tfStockMin;
        this.areaProductos = areaProductos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String codigo = tfCodigo.getText().trim();
        String descripcion = tfDescripcion.getText().trim();
        String precioStr = tfPrecio.getText().trim();
        String stockStr = tfStock.getText().trim();
        String stockMinStr = tfStockMin.getText().trim();

        List<Producto> filtrados = App.catalogo.listarProductos().stream()
                .filter(p -> codigo.isEmpty() || p.getCodigo().equals(codigo))
                .filter(p -> descripcion.isEmpty() || p.toString().toLowerCase().contains(descripcion.toLowerCase()))
                .filter(p -> filtrarPorPrecio(p, precioStr))
                .filter(p -> stockStr.isEmpty() || p.getCantidadStock() == Integer.parseInt(stockStr))
                .filter(p -> stockMinStr.isEmpty() || p.getStockMinimo() == Integer.parseInt(stockMinStr))
                .collect(Collectors.toList());

        areaProductos.append("\n╔═══════════════════════════════════════════════╗\n");
        areaProductos.append("║               RESULTADO DE BÚSQUEDA            ║\n");
        areaProductos.append("╠═══════════════════════════════════════════════╣\n");

        if (filtrados.isEmpty()) {
            areaProductos.append("No se encontraron productos con esos criterios.\n");
            areaProductos.append("Intente con diferentes parámetros de búsqueda.\n");
        } else {
            areaProductos.append("Se encontraron " + filtrados.size() + " producto(s):\n");
            areaProductos.append("─────────────────────────────────────────────────\n");
            for (int i = 0; i < filtrados.size(); i++) {
                Producto p = filtrados.get(i);
                areaProductos.append((i + 1) + ". " + p + "\n");
            }
        }
        areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
    }

    private boolean filtrarPorPrecio(Producto p, String precioStr) {
        if (precioStr.isEmpty()) return true;
        try {
            return p.getPrecioUnitario() == Double.parseDouble(precioStr);
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}