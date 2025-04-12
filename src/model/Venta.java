package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private List<ItemVenta> items;
    private Date fecha;
    private Pago pago;

    public Venta(Pago pago) {
        this.items = new ArrayList<>();
        this.fecha = new Date(); // Fecha de creación de la venta
        this.pago = pago;
    }

    // Agrega un item de venta después de validar que el producto tenga stock suficiente.
    public void agregarItem(Producto p, int cantidad) {
        if (!p.chequearStock(cantidad)) {
            System.out.println("Stock insuficiente para el producto: " + p);
        } else {
            ItemVenta item = new ItemVenta(p, cantidad);
            items.add(item);
        }
    }

    // Calcula el total de la venta aplicando el medio de pago seleccionado.
    public double calcularTotal() {
        double subtotal = 0;
        for (ItemVenta item : items) {
            subtotal += item.calcularSubtotal();
        }
        return pago.calcularMonto(subtotal);
    }

    // Registra la venta y reduce el stock de cada producto involucrado.
    public void registrarVenta() {
        for (ItemVenta item : items) {
            Producto p = item.getProducto();
            int cantidad = item.getCantidadVendida();
            if (p.chequearStock(cantidad)) {
                p.reducirStock(cantidad);
            } else {
                System.out.println("Error al registrar venta, stock insuficiente para: " + p);
            }
        }
        System.out.println("Venta registrada el " + fecha);
        System.out.println("Total de la venta: " + calcularTotal());
    }

    // Getters
    public List<ItemVenta> getItems() {
        return items;
    }

    public Date getFecha() {
        return fecha;
    }
}
