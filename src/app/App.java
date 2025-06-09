package app;

import javax.swing.*;
import model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;
    private static final int GRID_ROWS = 8;
    private static final int GRID_COLS = 2;
    
    private static CatalogoProductos catalogo;
    private static JTextField tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin;
    private static JTextArea areaProductos;
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::crearVentana);
    }

    public static void crearVentana() {
        inicializarComponentes();
        configurarVentana();
        crearPanelIngreso();
        crearAreaProductos();
        configurarEventos();
        frame.setVisible(true);
    }

    private static void inicializarComponentes() {
        catalogo = new CatalogoProductos();
        frame = new JFrame("Gestión de Productos");
        
        tfCodigo = new JTextField();
        tfDescripcion = new JTextField();
        tfPrecio = new JTextField();
        tfStock = new JTextField();
        tfStockMin = new JTextField();
        
        areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        areaProductos.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    }

    private static void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centrar ventana
    }

    private static void crearPanelIngreso() {
        JPanel panelIngreso = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, 5, 5));
        panelIngreso.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        
        // Campos de entrada
        agregarCampo(panelIngreso, "Código:", tfCodigo);
        agregarCampo(panelIngreso, "Descripción:", tfDescripcion);
        agregarCampo(panelIngreso, "Precio unitario:", tfPrecio);
        agregarCampo(panelIngreso, "Cantidad en stock:", tfStock);
        agregarCampo(panelIngreso, "Stock mínimo:", tfStockMin);

        // Botones
        JButton btnAgregar = crearBoton("Agregar producto");
        JButton btnCantidadProductos = crearBoton("Consultar cantidad");
        JButton btnBuscar = crearBoton("Buscar producto(s)");

        panelIngreso.add(btnAgregar);
        panelIngreso.add(btnCantidadProductos);
        panelIngreso.add(btnBuscar);
        panelIngreso.add(new JLabel()); // Espacio vacío

        frame.add(panelIngreso, BorderLayout.NORTH);
    }

    private static void agregarCampo(JPanel panel, String etiqueta, JTextField campo) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        panel.add(label);
        panel.add(campo);
    }

    private static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        return boton;
    }

    private static void crearAreaProductos() {
        JScrollPane scroll = new JScrollPane(areaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
        
        JButton btnMostrarStockMin = crearBoton("Mostrar productos con stock mínimo");
        btnMostrarStockMin.addActionListener(new MostrarStockMinimoListener());

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(btnMostrarStockMin, BorderLayout.SOUTH);
    }

    private static void configurarEventos() {
        // Buscar botones ya creados y asignar listeners
        Component[] componentes = ((JPanel) frame.getContentPane().getComponent(0)).getComponents();
        
        for (Component comp : componentes) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                switch (btn.getText()) {
                    case "Agregar producto":
                        btn.addActionListener(new AgregarProductoListener());
                        break;
                    case "Consultar cantidad":
                        btn.addActionListener(new ConsultarCantidadListener());
                        break;
                    case "Buscar producto(s)":
                        btn.addActionListener(new BuscarProductoListener());
                        break;
                }
            }
        }
    }

    private static void limpiarCampos() {
        tfCodigo.setText("");
        tfDescripcion.setText("");
        tfPrecio.setText("");
        tfStock.setText("");
        tfStockMin.setText("");
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Listeners como clases internas
    private static class AgregarProductoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = tfCodigo.getText().trim();
                String descripcion = tfDescripcion.getText().trim();
                
                if (codigo.isEmpty() || descripcion.isEmpty()) {
                    mostrarError("Código y descripción son obligatorios");
                    return;
                }
                
                double precio = Double.parseDouble(tfPrecio.getText());
                int stock = Integer.parseInt(tfStock.getText());
                int stockMin = Integer.parseInt(tfStockMin.getText());
                
                Producto producto = new Producto(codigo, descripcion, precio, stock, stockMin);
                catalogo.cargarProducto(producto);
                
                areaProductos.append("✓ Producto agregado: " + producto + "\n");
                limpiarCampos();
                
            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese valores numéricos válidos");
            } catch (Exception ex) {
                mostrarError("Error al agregar producto: " + ex.getMessage());
            }
        }
    }

    private static class MostrarStockMinimoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            areaProductos.append("\n=== PRODUCTOS CON STOCK MÍNIMO ===\n");
            List<Producto> productosStockMin = catalogo.marcarStockMinimo();
            
            if (productosStockMin.isEmpty()) {
                areaProductos.append("No hay productos con stock mínimo.\n");
            } else {
                for (Producto p : productosStockMin) {
                    areaProductos.append("⚠️ " + p + "\n");
                }
            }
            areaProductos.append("================================\n\n");
        }
    }

    private static class ConsultarCantidadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int cantidad = catalogo.listarProductos().size();
            areaProductos.append("\n📊 RESUMEN DEL CATÁLOGO 📊\n");
            areaProductos.append("Cantidad total de productos: " + cantidad + "\n");
            areaProductos.append("=== LISTA COMPLETA ===\n");
            
            if (cantidad == 0) {
                areaProductos.append("El catálogo está vacío.\n");
            } else {
                for (Producto p : catalogo.listarProductos()) {
                    areaProductos.append("• " + p + "\n");
                }
            }
            areaProductos.append("=====================\n\n");
        }
    }

    private static class BuscarProductoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codigo = tfCodigo.getText().trim();
            String descripcion = tfDescripcion.getText().trim();
            String precioStr = tfPrecio.getText().trim();
            String stockStr = tfStock.getText().trim();
            String stockMinStr = tfStockMin.getText().trim();

            List<Producto> filtrados = catalogo.listarProductos().stream()
                .filter(p -> codigo.isEmpty() || p.toString().contains("Código: " + codigo + " "))
                .filter(p -> descripcion.isEmpty() || 
                    p.toString().toLowerCase().contains(descripcion.toLowerCase()))
                .filter(p -> filtrarPorPrecio(p, precioStr))
                .filter(p -> filtrarPorStock(p, stockStr))
                .filter(p -> filtrarPorStockMin(p, stockMinStr))
                .collect(Collectors.toList());

            mostrarResultadoBusqueda(filtrados);
        }

        private boolean filtrarPorPrecio(Producto p, String precioStr) {
            if (precioStr.isEmpty()) return true;
            try { 
                return p.getPrecioUnitario() == Double.parseDouble(precioStr); 
            } catch (NumberFormatException ex) { 
                return false; 
            }
        }

        private boolean filtrarPorStock(Producto p, String stockStr) {
            if (stockStr.isEmpty()) return true;
            try {
                return p.toString().contains("Stock: " + Integer.parseInt(stockStr) + " ");
            } catch (NumberFormatException ex) { 
                return false; 
            }
        }

        private boolean filtrarPorStockMin(Producto p, String stockMinStr) {
            if (stockMinStr.isEmpty()) return true;
            try {
                return p.toString().contains("Stock mínimo: " + Integer.parseInt(stockMinStr));
            } catch (NumberFormatException ex) { 
                return false; 
            }
        }

        private void mostrarResultadoBusqueda(List<Producto> filtrados) {
            areaProductos.append("\n🔍 RESULTADO DE BÚSQUEDA 🔍\n");
            if (filtrados.isEmpty()) {
                areaProductos.append("❌ No se encontraron productos con esos criterios.\n");
            } else {
                areaProductos.append("Se encontraron " + filtrados.size() + " producto(s):\n");
                for (Producto p : filtrados) {
                    areaProductos.append("🎯 " + p + "\n");
                }
            }
            areaProductos.append("===========================\n\n");
        }
    }
}