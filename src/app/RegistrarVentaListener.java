package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Producto;
import model.Pago;
import model.PagoEfectivo;
import model.PagoDebito;
import model.PagoCredito;
import model.Venta;

public class RegistrarVentaListener implements ActionListener {
    private final JTextField tfCodigoVenta, tfCantidadVenta;
    private final JComboBox<String> cbMedioPago;
    private final JTextArea areaProductos;
    private final JFrame frame;

    public RegistrarVentaListener(JTextField tfCodigoVenta, JTextField tfCantidadVenta, JComboBox<String> cbMedioPago, JTextArea areaProductos, JFrame frame) {
        this.tfCodigoVenta = tfCodigoVenta;
        this.tfCantidadVenta = tfCantidadVenta;
        this.cbMedioPago = cbMedioPago;
        this.areaProductos = areaProductos;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String codigo = tfCodigoVenta.getText().trim();
        String cantidadStr = tfCantidadVenta.getText().trim();

        if (codigo.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Debe ingresar código y cantidad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(frame, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto prod = App.catalogo.buscarPorCodigo(codigo);

        if (prod == null) {
            JOptionPane.showMessageDialog(frame, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int stockActual = prod.getCantidadStock();
        int stockMinimo = prod.getStockMinimo();

        if (!prod.chequearStock(cantidad)) {
            JOptionPane.showMessageDialog(frame, "Stock insuficiente. Stock disponible: " + stockActual, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int stockDespuesVenta = stockActual - cantidad;
        if (stockDespuesVenta < stockMinimo) {
            JOptionPane.showMessageDialog(frame,
                    "No se puede realizar la venta.\n" +
                    "Stock actual: " + stockActual + "\n" +
                    "Stock mínimo: " + stockMinimo + "\n" +
                    "Cantidad máxima vendible: " + (stockActual - stockMinimo),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pago pago;
        String medio = (String) cbMedioPago.getSelectedItem();
        try {
            switch (medio) {
                case "Efectivo": pago = new PagoEfectivo(); break;
                case "Débito": pago = new PagoDebito(); break;
                case "Crédito (2 cuotas)": pago = new PagoCredito(2); break;
                case "Crédito (3 cuotas)": pago = new PagoCredito(3); break;
                case "Crédito (6 cuotas)": pago = new PagoCredito(6); break;
                default: pago = new PagoDebito();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error en el medio de pago: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Venta venta = new Venta(pago);
        venta.agregarItem(prod, cantidad);
        double total = venta.calcularTotal();
        venta.registrarVenta();

        areaProductos.append("╔═══════════════════════════════════════════════╗\n");
        areaProductos.append("║                VENTA REGISTRADA                ║\n");
        areaProductos.append("╠═══════════════════════════════════════════════╣\n");
        areaProductos.append("Producto: " + prod + "\n");
        areaProductos.append("Cantidad vendida: " + cantidad + "\n");
        areaProductos.append("Stock restante: " + (stockActual - cantidad) + "\n");
        areaProductos.append("Medio de pago: " + medio + "\n");
        areaProductos.append("Total a pagar: $" + String.format("%.2f", total) + "\n");
        areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");

        tfCodigoVenta.setText("");
        tfCantidadVenta.setText("");
        // Se eliminó el JOptionPane de éxito
    }
}