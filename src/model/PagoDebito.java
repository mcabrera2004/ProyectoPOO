package model;

public class PagoDebito extends Pago {
    @Override
    public double calcularMonto(double total) {
        // En débito se cobra el total sin modificaciones.
        return total;
    }
}
