package model;

public class ItemVenta {
    private Producto producto;
    private int cantidadVendida;

    public ItemVenta(Producto producto, int cantidadVendida) {
        this.producto = producto;
        this.cantidadVendida = cantidadVendida;
    }

    // Calcula el subtotal del item: precio unitario x cantidad vendida.
    public double calcularSubtotal() {
        return producto.getPrecioUnitario() * cantidadVendida;
    }

    // Getters para poder acceder a los atributos en la clase Venta
    public Producto getProducto() {
        return producto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }
}
