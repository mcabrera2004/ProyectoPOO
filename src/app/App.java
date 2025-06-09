package app;

import javax.swing.*;
import model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;
    private static final int GRID_ROWS = 8;
    private static final int GRID_COLS = 2;
    
    // Colores del tema moderno
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(18, 18, 18);
    private static final Color COLOR_FONDO_SECUNDARIO = new Color(30, 30, 30);
    private static final Color COLOR_ACENTO = new Color(0, 150, 255);
    private static final Color COLOR_ACENTO_HOVER = new Color(0, 120, 200);
    private static final Color COLOR_TEXTO = new Color(255, 255, 255);
    private static final Color COLOR_EXITO = new Color(46, 204, 113);
    private static final Color COLOR_PELIGRO = new Color(231, 76, 60);
    private static final Color COLOR_ADVERTENCIA = new Color(241, 196, 15);
    
    private static CatalogoProductos catalogo;
    private static JTextField tfCodigo, tfDescripcion, tfPrecio, tfStock, tfStockMin;
    private static JTextArea areaProductos;
    private static JFrame frame;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        frame = new JFrame("Sistema de Gestión de Productos v2.0");
        frame.getContentPane().setBackground(COLOR_FONDO_PRINCIPAL);
        
        tfCodigo = crearTextField();
        tfDescripcion = crearTextField();
        tfPrecio = crearTextField();
        tfStock = crearTextField();
        tfStockMin = crearTextField();
        
        areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        areaProductos.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        areaProductos.setBackground(COLOR_FONDO_SECUNDARIO);
        areaProductos.setForeground(COLOR_TEXTO);
        areaProductos.setCaretColor(COLOR_ACENTO);
        areaProductos.setMargin(new Insets(10, 10, 10, 10));
    }

    private static JTextField crearTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBackground(COLOR_FONDO_SECUNDARIO);
        textField.setForeground(COLOR_TEXTO);
        textField.setCaretColor(COLOR_ACENTO);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACENTO, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_ACENTO_HOVER, 2),
                    BorderFactory.createEmptyBorder(4, 7, 4, 7)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!textField.hasFocus()) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_ACENTO, 1),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                    ));
                }
            }
        });
        
        return textField;
    }

    private static void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);
        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private static void crearPanelIngreso() {
        JPanel panelIngreso = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, 10, 10));
        panelIngreso.setBackground(COLOR_FONDO_SECUNDARIO);
        panelIngreso.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACENTO, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                "Datos del Producto",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                COLOR_TEXTO
            )
        ));
        
        agregarCampo(panelIngreso, "Código:", tfCodigo);
        agregarCampo(panelIngreso, "Descripción:", tfDescripcion);
        agregarCampo(panelIngreso, "Precio unitario:", tfPrecio);
        agregarCampo(panelIngreso, "Cantidad en stock:", tfStock);
        agregarCampo(panelIngreso, "Stock mínimo:", tfStockMin);

        JButton btnAgregar = crearBotonModerno("Agregar Producto", COLOR_EXITO);
        JButton btnCantidadProductos = crearBotonModerno("Consultar Cantidad", COLOR_ACENTO);
        JButton btnBuscar = crearBotonModerno("Buscar Producto(s)", COLOR_ADVERTENCIA);

        panelIngreso.add(btnAgregar);
        panelIngreso.add(btnCantidadProductos);
        panelIngreso.add(btnBuscar);
        panelIngreso.add(new JLabel());

        frame.add(panelIngreso, BorderLayout.NORTH);
    }

    private static void agregarCampo(JPanel panel, String etiqueta, JTextField campo) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(COLOR_TEXTO);
        panel.add(label);
        panel.add(campo);
    }

    private static JButton crearBotonModerno(String texto, Color colorBase) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, colorBase,
                    0, getHeight(), colorBase.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
        };
        
        boton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        boton.setPreferredSize(new Dimension(180, 35));
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorBase.brighter());
                boton.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorBase);
                boton.repaint();
            }
        });
        
        return boton;
    }

    private static void crearAreaProductos() {
        JScrollPane scroll = new JScrollPane(areaProductos);
        scroll.setBackground(COLOR_FONDO_SECUNDARIO);
        scroll.getViewport().setBackground(COLOR_FONDO_SECUNDARIO);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACENTO, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                "Lista de Productos",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                COLOR_TEXTO
            )
        ));
        
        scroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scroll.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        
        JButton btnMostrarStockMin = crearBotonModerno("Mostrar Stock Mínimo", COLOR_PELIGRO);
        btnMostrarStockMin.addActionListener(new MostrarStockMinimoListener());

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(btnMostrarStockMin, BorderLayout.SOUTH);
    }

    private static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = COLOR_ACENTO;
            trackColor = COLOR_FONDO_SECUNDARIO;
        }
        
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createInvisibleButton();
        }
        
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createInvisibleButton();
        }
        
        private JButton createInvisibleButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
    }

    private static void configurarEventos() {
        Component[] componentes = ((JPanel) frame.getContentPane().getComponent(0)).getComponents();
        
        for (Component comp : componentes) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                String texto = btn.getText();
                if (texto.contains("Agregar")) {
                    btn.addActionListener(new AgregarProductoListener());
                } else if (texto.contains("Consultar")) {
                    btn.addActionListener(new ConsultarCantidadListener());
                } else if (texto.contains("Buscar")) {
                    btn.addActionListener(new BuscarProductoListener());
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
        JOptionPane optionPane = new JOptionPane(
            mensaje, 
            JOptionPane.ERROR_MESSAGE, 
            JOptionPane.DEFAULT_OPTION, 
            null, 
            new Object[]{"Aceptar"}, 
            "Aceptar"
        );
        
        JDialog dialog = optionPane.createDialog(frame, "Error");
        dialog.getContentPane().setBackground(COLOR_FONDO_PRINCIPAL);
        dialog.setVisible(true);
    }

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
                
                areaProductos.append("PRODUCTO AGREGADO EXITOSAMENTE\n");
                areaProductos.append("════════════════════════════════════════\n");
                areaProductos.append(producto + "\n");
                areaProductos.append("════════════════════════════════════════\n\n");
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
            areaProductos.append("\n═══════════════════════════════════════\n");
            areaProductos.append("PRODUCTOS CON STOCK MÍNIMO ALCANZADO\n");
            areaProductos.append("═══════════════════════════════════════\n");
            
            List<Producto> productosStockMin = catalogo.marcarStockMinimo();
            
            if (productosStockMin.isEmpty()) {
                areaProductos.append("No hay productos con stock mínimo.\n");
                areaProductos.append("Todos los productos tienen stock suficiente.\n");
            } else {
                areaProductos.append("Se encontraron " + productosStockMin.size() + " producto(s):\n\n");
                for (Producto p : productosStockMin) {
                    areaProductos.append(p + "\n");
                }
            }
            areaProductos.append("══════════════════════════════════════════════\n\n");
        }
    }

    private static class ConsultarCantidadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int cantidad = catalogo.listarProductos().size();
            areaProductos.append("\n═══════════════════════════════════════\n");
            areaProductos.append("RESUMEN DEL CATÁLOGO\n");
            areaProductos.append("═══════════════════════════════════════\n");
            areaProductos.append("Cantidad total de productos: " + cantidad + "\n");
            areaProductos.append("───────────────────────────────────────────────\n");
            areaProductos.append("LISTA COMPLETA DE PRODUCTOS:\n");
            areaProductos.append("───────────────────────────────────────────────\n");
            
            if (cantidad == 0) {
                areaProductos.append("El catálogo está vacío.\n");
                areaProductos.append("Agregue productos para comenzar.\n");
            } else {
                for (int i = 0; i < catalogo.listarProductos().size(); i++) {
                    Producto p = catalogo.listarProductos().get(i);
                    areaProductos.append((i + 1) + ". " + p + "\n");
                }
            }
            areaProductos.append("═══════════════════════════════════════════════\n\n");
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
            areaProductos.append("\n═══════════════════════════════════════\n");
            areaProductos.append("RESULTADO DE BÚSQUEDA\n");
            areaProductos.append("═══════════════════════════════════════\n");
            
            if (filtrados.isEmpty()) {
                areaProductos.append("No se encontraron productos con esos criterios.\n");
                areaProductos.append("Intente con diferentes parámetros de búsqueda.\n");
            } else {
                areaProductos.append("Se encontraron " + filtrados.size() + " producto(s):\n");
                areaProductos.append("───────────────────────────────────────────────\n");
                for (int i = 0; i < filtrados.size(); i++) {
                    Producto p = filtrados.get(i);
                    areaProductos.append((i + 1) + ". " + p + "\n");
                }
            }
            areaProductos.append("═══════════════════════════════════════════════\n\n");
        }
    }
}