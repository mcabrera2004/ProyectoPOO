package model;

import java.util.ArrayList;
import java.util.List;

public class CatalogoProductos {
    private List<Producto> productos;

    public CatalogoProductos() {
        productos = new ArrayList<>();
    }

    // Agrega un nuevo producto al catálogo
    public void cargarProducto(Producto p) {
        productos.add(p);
    }

    // Retorna la lista completa de productos
    public List<Producto> listarProductos() {
        return productos;
    }

    // Retorna una lista de productos cuyo stock actual sea menor o igual al stock mínimo
    public List<Producto> marcarStockMinimo() {
        List<Producto> productosConStockMinimo = new ArrayList<>();
        for (Producto p : productos) {
            if (p.esStockMinimo()) {
                productosConStockMinimo.add(p);
            }
        }
        return productosConStockMinimo;
    }

    // Busca un producto por su código
    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }
}