package model;

public class PagoCredito extends Pago {
    private int cuotas;

    public PagoCredito(int cuotas) {
        // Validar que el número de cuotas sea 2, 3 o 6.
        if(cuotas != 2 && cuotas != 3 && cuotas != 6) {
            throw new IllegalArgumentException("Las cuotas deben ser 2, 3 o 6");
        }
        this.cuotas = cuotas;
    }

    @Override
    public double calcularMonto(double total) {
        double recargo = 0;
        switch(cuotas) {
            case 2:
                recargo = 0.06; // 6% de recargo
                break;
            case 3:
                recargo = 0.12; // 12% de recargo
                break;
            case 6:
                recargo = 0.20; // 20% de recargo
                break;
        }
        return total * (1 + recargo);
    }
}

