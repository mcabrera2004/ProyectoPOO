package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Producto;

public class ActualizarProductoListener implements ActionListener {
    private final JTextField tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin;
    private final JTextArea areaProductos;
    private final JFrame frame;

    public ActualizarProductoListener(JTextField tfCodigo, JTextField tfDescripcion, JTextField tfPrecio, JTextField tfStock, JTextField tfStockMin, JTextArea areaProductos, JFrame frame) {
        this.tfCodigo = tfCodigo;
        this.tfDescripcion = tfDescripcion;
        this.tfPrecio = tfPrecio;
        this.tfStock = tfStock;
        this.tfStockMin = tfStockMin;
        this.areaProductos = areaProductos;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String codigo = tfCodigo.getText().trim();

            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Debe ingresar el código del producto a actualizar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoExistente = App.catalogo.buscarPorCodigo(codigo);
            if (productoExistente == null) {
                JOptionPane.showMessageDialog(frame, "No existe un producto con el código: " + codigo, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descripcion = tfDescripcion.getText().trim();
            String precioStr = tfPrecio.getText().trim();
            String stockStr = tfStock.getText().trim();
            String stockMinStr = tfStockMin.getText().trim();

            if (descripcion.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || stockMinStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Debe completar todos los campos para actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);
            int stockMin = Integer.parseInt(stockMinStr);

            if (precio <= 0 || stock < 0 || stockMin < 0) {
                JOptionPane.showMessageDialog(frame, "Los valores numéricos deben ser positivos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stock < stockMin) {
                int respuesta = JOptionPane.showConfirmDialog(
                        frame,
                        "El nuevo stock (" + stock + ") es menor al stock mínimo (" + stockMin + ").\n¿Desea continuar?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (respuesta != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            App.catalogo.listarProductos().remove(productoExistente);
            Producto productoActualizado = new Producto(codigo, descripcion, precio, stock, stockMin);
            App.catalogo.cargarProducto(productoActualizado);

            areaProductos.append("╔════════════════════════════════════════════════╗\n");
            areaProductos.append("║          PRODUCTO ACTUALIZADO EXITOSAMENTE     ║\n");
            areaProductos.append("╠════════════════════════════════════════════════╣\n");
            areaProductos.append("ANTES: " + productoExistente + "\n");
            areaProductos.append("DESPUÉS: " + productoActualizado + "\n");
            if (stock < stockMin) {
                areaProductos.append("⚠️  ATENCIÓN: Producto con stock por debajo del mínimo\n");
            }
            areaProductos.append("╚════════════════════════════════════════════════╝\n\n");

            tfCodigo.setText("");
            tfDescripcion.setText("");
            tfPrecio.setText("");
            tfStock.setText("");
            tfStockMin.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al actualizar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}