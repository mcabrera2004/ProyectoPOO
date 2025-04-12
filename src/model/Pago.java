package model;

public abstract class Pago {
    // Método abstracto para calcular el total final de la venta según el medio de pago
    public abstract double calcularMonto(double total);
}

