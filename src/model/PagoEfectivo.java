package model;

public class PagoEfectivo extends Pago {
    @Override
    public double calcularMonto(double total) {
        // En efectivo se aplica un descuento del 10%
        return total * 0.90;
    }
}

