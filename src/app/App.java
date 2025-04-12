package app;

import model.*;

public class App {
    public static void main(String[] args) {
        // Crear el catálogo
        CatalogoProductos catalogo = new CatalogoProductos();

        // Agregar productos
        Producto arroz = new Producto("P001", "Arroz", 500.0, 100, 10);
        Producto fideos = new Producto("P002", "Fideos", 350.0, 5, 5); // stock mínimo alcanzado

        catalogo.cargarProducto(arroz);
        catalogo.cargarProducto(fideos);

        // Mostrar productos con stock mínimo
        System.out.println("Productos con stock mínimo:");
        for (Producto p : catalogo.marcarStockMinimo()) {
            System.out.println(p);
        }

        // Crear una venta
        Pago pago = new PagoEfectivo();
        Venta venta = new Venta(pago);

        // Intentar agregar productos a la venta
        venta.agregarItem(arroz, 3); // stock suficiente
        venta.agregarItem(fideos, 10); // stock insuficiente (solo hay 5)

        // Registrar la venta
        venta.registrarVenta();
    }
}

