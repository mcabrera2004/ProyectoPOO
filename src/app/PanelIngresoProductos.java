package app;

import javax.swing.*;
import java.awt.*;

public class PanelIngresoProductos extends JPanel {
    private final JTextField tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin;

    public PanelIngresoProductos(JFrame frame, JTextArea areaProductos) {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBackground(App.COLOR_FONDO_SECUNDARIO);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(App.COLOR_ACENTO, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                "Gestión de Productos",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                App.COLOR_TEXTO
            )
        ));

        tfCodigo = UtilidadesSwing.crearTextField();
        tfDescripcion = UtilidadesSwing.crearTextField();
        tfPrecio = UtilidadesSwing.crearTextField();
        tfStock = UtilidadesSwing.crearTextField();
        tfStockMin = UtilidadesSwing.crearTextField();

        add(UtilidadesSwing.crearLabel("Código:")); add(tfCodigo);
        add(UtilidadesSwing.crearLabel("Descripción:")); add(tfDescripcion);
        add(UtilidadesSwing.crearLabel("Precio unitario:")); add(tfPrecio);
        add(UtilidadesSwing.crearLabel("Cantidad en stock:")); add(tfStock);
        add(UtilidadesSwing.crearLabel("Stock mínimo:")); add(tfStockMin);

        JButton btnAgregar = UtilidadesSwing.crearBotonModerno("Agregar Producto", App.COLOR_EXITO);
        JButton btnCantidadProductos = UtilidadesSwing.crearBotonModerno("Consultar Cantidad", App.COLOR_ACENTO);
        JButton btnBuscar = UtilidadesSwing.crearBotonModerno("Buscar Producto(s)", App.COLOR_ADVERTENCIA);
        JButton btnActualizar = UtilidadesSwing.crearBotonModerno("Actualizar Producto", App.COLOR_NARANJA);

        add(btnAgregar); add(btnCantidadProductos);
        add(btnBuscar); add(btnActualizar);

        // PASA areaProductos a cada listener
        btnAgregar.addActionListener(new AgregarProductoListener(tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin, areaProductos, frame));
        btnActualizar.addActionListener(new ActualizarProductoListener(tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin, areaProductos, frame));
        btnCantidadProductos.addActionListener(new ConsultarCantidadListener(areaProductos));
        btnBuscar.addActionListener(new BuscarProductoListener(tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin, areaProductos));
    }
}