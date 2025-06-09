package model;

public class Producto {
    private String codigo;
    private String descripcion;
    private double precioUnitario;
    private int cantidadStock;
    private int stockMinimo;

    public Producto(String codigo, String descripcion, double precioUnitario, int cantidadStock, int stockMinimo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
    }

    // Método para verificar si hay stock suficiente para una cantidad dada
    public boolean chequearStock(int cantidad) {
        return this.cantidadStock >= cantidad;
    }

    // Método para reducir la cantidad de stock al realizar una venta
    public void reducirStock(int cantidad) {
        if (chequearStock(cantidad)) {
            this.cantidadStock -= cantidad;
        } else {
            System.out.println("No se puede reducir stock. Stock insuficiente para " + this.descripcion);
        }
    }
    
    // Retorna true si el stock actual está en o debajo del stock mínimo.
    public boolean esStockMinimo() {
        return this.cantidadStock <= this.stockMinimo;
    }
    
    // Getter necesario para calcular el subtotal en ItemVenta
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    
    @Override
    public String toString() {
        return "Código: " + codigo +
            " | Descripción: " + descripcion +
            " | Precio: $" + precioUnitario +
            " | Stock: " + cantidadStock +
            " | Stock mínimo: " + stockMinimo;
    }
}

