package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Producto;

public class AgregarProductoListener implements ActionListener {
    private final JTextField tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin;
    private final JTextArea areaProductos;
    private final JFrame frame;

    public AgregarProductoListener(JTextField tfCodigo, JTextField tfDescripcion, JTextField tfPrecio, JTextField tfStock, JTextField tfStockMin, JTextArea areaProductos, JFrame frame) {
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
            String descripcion = tfDescripcion.getText().trim();

            if (codigo.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Código y descripción son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoExistente = App.catalogo.buscarPorCodigo(codigo);
            if (productoExistente != null) {
                JOptionPane.showMessageDialog(frame, "Ya existe un producto con el código: " + codigo, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(tfPrecio.getText());
            int stock = Integer.parseInt(tfStock.getText());
            int stockMin = Integer.parseInt(tfStockMin.getText());

            if (precio <= 0 || stock < 0 || stockMin < 0) {
                JOptionPane.showMessageDialog(frame, "Los valores numéricos deben ser positivos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stock < stockMin) {
                int respuesta = JOptionPane.showConfirmDialog(
                        frame,
                        "El stock inicial (" + stock + ") es menor al stock mínimo (" + stockMin + ").\n¿Desea continuar?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (respuesta != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            Producto producto = new Producto(codigo, descripcion, precio, stock, stockMin);
            App.catalogo.cargarProducto(producto);

            areaProductos.append("╔════════════════════════════════════════════════╗\n");
            areaProductos.append("║            PRODUCTO AGREGADO EXITOSAMENTE      ║\n");
            areaProductos.append("╠════════════════════════════════════════════════╣\n");
            areaProductos.append(producto + "\n");
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
            JOptionPane.showMessageDialog(frame, "Error al agregar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}