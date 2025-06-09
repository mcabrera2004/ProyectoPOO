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
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 750;
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
    private static final Color COLOR_NARANJA = new Color(255, 143, 0);
    
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
        crearPanelVenta();
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

    private static JComboBox<String> crearComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBackground(COLOR_FONDO_SECUNDARIO);
        comboBox.setForeground(COLOR_TEXTO);
        comboBox.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 1));
        
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? COLOR_ACENTO : COLOR_FONDO_SECUNDARIO);
                setForeground(COLOR_TEXTO);
                setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                return this;
            }
        });
        
        return comboBox;
    }

    private static JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(COLOR_TEXTO);
        return label;
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
                "Gestión de Productos",
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
        JButton btnActualizar = crearBotonModerno("Actualizar Producto", COLOR_NARANJA);

        panelIngreso.add(btnAgregar);
        panelIngreso.add(btnCantidadProductos);
        panelIngreso.add(btnBuscar);
        panelIngreso.add(btnActualizar);

        frame.add(panelIngreso, BorderLayout.NORTH);
    }

    private static void agregarCampo(JPanel panel, String etiqueta, JTextField campo) {
        JLabel label = crearLabel(etiqueta);
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

    private static void crearPanelVenta() {
        JPanel panelVenta = new JPanel(new GridLayout(6, 2, 10, 10));
        panelVenta.setBackground(COLOR_FONDO_SECUNDARIO);
        panelVenta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACENTO, 2),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                "Registrar Venta",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                COLOR_TEXTO
            )
        ));
        panelVenta.setPreferredSize(new Dimension(300, 0));

        JTextField tfCodigoVenta = crearTextField();
        JTextField tfCantidadVenta = crearTextField();
        String[] medios = {"Efectivo", "Débito", "Crédito (2 cuotas)", "Crédito (3 cuotas)", "Crédito (6 cuotas)"};
        JComboBox<String> cbMedioPago = crearComboBox(medios);

        JButton btnRegistrarVenta = crearBotonModerno("Registrar Venta", COLOR_EXITO);
        btnRegistrarVenta.setPreferredSize(new Dimension(120, 35));

        panelVenta.add(crearLabel("Código producto:"));
        panelVenta.add(tfCodigoVenta);
        panelVenta.add(crearLabel("Cantidad:"));
        panelVenta.add(tfCantidadVenta);
        panelVenta.add(crearLabel("Medio de pago:"));
        panelVenta.add(cbMedioPago);
        
        JPanel espaciador = new JPanel();
        espaciador.setBackground(COLOR_FONDO_SECUNDARIO);
        panelVenta.add(espaciador);
        panelVenta.add(btnRegistrarVenta);
        
        // Área de información dinámica
        JTextArea infoVenta = new JTextArea(3, 20);
        infoVenta.setEditable(false);
        infoVenta.setBackground(COLOR_FONDO_SECUNDARIO);
        infoVenta.setForeground(COLOR_TEXTO);
        infoVenta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        infoVenta.setLineWrap(false);
        infoVenta.setWrapStyleWord(false);
        infoVenta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ADVERTENCIA, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        // Actualizar información según medio de pago
        Runnable actualizarInfo = () -> {
            String medio = (String) cbMedioPago.getSelectedItem();
            String texto = "";
            Color colorBorde = COLOR_ADVERTENCIA;
            
            switch (medio) {
                case "Efectivo":
                    texto = "DESCUENTO: 10%";
                    colorBorde = COLOR_EXITO;
                    break;
                case "Débito":
                    texto = "SIN RECARGO";
                    colorBorde = COLOR_ACENTO;
                    break;
                case "Crédito (2 cuotas)":
                    texto = "RECARGO: 6%";
                    colorBorde = COLOR_ADVERTENCIA;
                    break;
                case "Crédito (3 cuotas)":
                    texto = "RECARGO: 12%";
                    colorBorde = COLOR_ADVERTENCIA;
                    break;
                case "Crédito (6 cuotas)":
                    texto = "RECARGO: 20%";
                    colorBorde = COLOR_PELIGRO;
                    break;
            }
            
            infoVenta.setText(texto);
            infoVenta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
        };
        
        actualizarInfo.run();
        cbMedioPago.addActionListener(e -> actualizarInfo.run());
        
        panelVenta.add(new JLabel());
        panelVenta.add(infoVenta);

        frame.add(panelVenta, BorderLayout.EAST);

        btnRegistrarVenta.addActionListener(e -> {
            String codigo = tfCodigoVenta.getText().trim();
            String cantidadStr = tfCantidadVenta.getText().trim();
            
            if (codigo.isEmpty() || cantidadStr.isEmpty()) {
                mostrarError("Debe ingresar código y cantidad");
                return;
            }
            
            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    mostrarError("La cantidad debe ser mayor a 0");
                    return;
                }
            } catch (NumberFormatException ex) {
                mostrarError("Cantidad inválida");
                return;
            }
            
            Producto prod = catalogo.buscarPorCodigo(codigo);
                
            if (prod == null) {
                mostrarError("Producto no encontrado");
                return;
            }
            
            // Obtener stock actual y mínimo del producto
            int stockActual = prod.getCantidadStock();
            int stockMinimo = prod.getStockMinimo();
            
            // Verificar si hay stock suficiente
            if (!prod.chequearStock(cantidad)) {
                mostrarError("Stock insuficiente. Stock disponible: " + stockActual);
                return;
            }
            
            //Verificar que después de la venta no quede por debajo del stock mínimo
            int stockDespuesVenta = stockActual - cantidad;
            if (stockDespuesVenta < stockMinimo) {
                mostrarError("No se puede realizar la venta.\n" +
                           "Stock actual: " + stockActual + "\n" +
                           "Stock mínimo: " + stockMinimo + "\n" +
                           "Cantidad máxima vendible: " + (stockActual - stockMinimo));
                return;
            }
            
            Pago pago;
            String medio = (String) cbMedioPago.getSelectedItem();
            try {
                switch (medio) {
                    case "Efectivo": pago = new PagoEfectivo(); break;
                    case "Débito": pago = new PagoDebito(); break;
                    case "Crédito (2 cuotas)": pago = new PagoCredito(2); break;
                    case "Crédito (3 cuotas)": pago = new PagoCredito(3); break;
                    case "Crédito (6 cuotas)": pago = new PagoCredito(6); break;
                    default: pago = new PagoDebito();
                }
            } catch (Exception ex) {
                mostrarError("Error en el medio de pago: " + ex.getMessage());
                return;
            }
            
            Venta venta = new Venta(pago);
            venta.agregarItem(prod, cantidad);
            double total = venta.calcularTotal();
            venta.registrarVenta();
            
            areaProductos.append("╔═══════════════════════════════════════════════╗\n");
            areaProductos.append("║                VENTA REGISTRADA                ║\n");
            areaProductos.append("╠═══════════════════════════════════════════════╣\n");
            areaProductos.append("Producto: " + prod + "\n");
            areaProductos.append("Cantidad vendida: " + cantidad + "\n");
            areaProductos.append("Stock restante: " + (stockActual - cantidad) + "\n");
            areaProductos.append("Medio de pago: " + medio + "\n");
            areaProductos.append("Total a pagar: $" + String.format("%.2f", total) + "\n");
            areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
            
            tfCodigoVenta.setText("");
            tfCantidadVenta.setText("");
            
            mostrarExito("Venta registrada exitosamente!");
        });
    }

    private static void mostrarExito(String mensaje) {
        JOptionPane optionPane = new JOptionPane(
            mensaje, 
            JOptionPane.INFORMATION_MESSAGE, 
            JOptionPane.DEFAULT_OPTION, 
            null, 
            new Object[]{"Aceptar"}, 
            "Aceptar"
        );
        
        JDialog dialog = optionPane.createDialog(frame, "Éxito");
        dialog.getContentPane().setBackground(COLOR_FONDO_PRINCIPAL);
        dialog.setVisible(true);
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
                } else if (texto.contains("Actualizar")) {
                    btn.addActionListener(new ActualizarProductoListener());
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
                
                //Verificar si ya existe un producto con ese código
                Producto productoExistente = catalogo.buscarPorCodigo(codigo);
                if (productoExistente != null) {
                    mostrarError("Ya existe un producto con el código: " + codigo + "\n" +
                               "No se pueden tener productos duplicados.\n" +
                               "Use 'Actualizar Producto' para modificarlo.");
                    return;
                }
                
                double precio = Double.parseDouble(tfPrecio.getText());
                int stock = Integer.parseInt(tfStock.getText());
                int stockMin = Integer.parseInt(tfStockMin.getText());
                
                if (precio <= 0 || stock < 0 || stockMin < 0) {
                    mostrarError("Los valores numéricos deben ser positivos");
                    return;
                }
                
                // Validación adicional: el stock inicial debería ser >= stock mínimo
                if (stock < stockMin) {
                    int respuesta = JOptionPane.showConfirmDialog(
                        frame,
                        "⚠️ ADVERTENCIA ⚠️\n\n" +
                        "El stock inicial (" + stock + ") es menor al stock mínimo (" + stockMin + ").\n" +
                        "Esto significa que el producto estará inmediatamente en estado de stock mínimo.\n\n" +
                        "¿Desea continuar de todas formas?",
                        "Stock por debajo del mínimo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (respuesta != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                Producto producto = new Producto(codigo, descripcion, precio, stock, stockMin);
                catalogo.cargarProducto(producto);
                
                areaProductos.append("╔════════════════════════════════════════════════╗\n");
                areaProductos.append("║            PRODUCTO AGREGADO EXITOSAMENTE      ║\n");
                areaProductos.append("╠════════════════════════════════════════════════╣\n");
                areaProductos.append(producto + "\n");
                if (stock < stockMin) {
                    areaProductos.append("⚠️  ATENCIÓN: Producto con stock por debajo del mínimo\n");
                }
                areaProductos.append("╚════════════════════════════════════════════════╝\n\n");
                limpiarCampos();
                
            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese valores numéricos válidos");
            } catch (Exception ex) {
                mostrarError("Error al agregar producto: " + ex.getMessage());
            }
        }
    }

    private static class ActualizarProductoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = tfCodigo.getText().trim();
                
                if (codigo.isEmpty()) {
                    mostrarError("Debe ingresar el código del producto a actualizar");
                    return;
                }
                
                // Buscar el producto existente
                Producto productoExistente = catalogo.buscarPorCodigo(codigo);
                if (productoExistente == null) {
                    mostrarError("No existe un producto con el código: " + codigo + "\n" +
                               "Verifique el código o use 'Agregar Producto' para crear uno nuevo.");
                    return;
                }
                
                // Mostrar datos actuales del producto
                String datosActuales = "Producto encontrado:\n" + productoExistente + "\n\n" +
                                     "¿Desea continuar con la actualización?";
                
                int confirmacion = JOptionPane.showConfirmDialog(
                    frame,
                    datosActuales,
                    "Confirmar Actualización",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }
                
                // Validar que se hayan ingresado los nuevos datos
                String descripcion = tfDescripcion.getText().trim();
                String precioStr = tfPrecio.getText().trim();
                String stockStr = tfStock.getText().trim();
                String stockMinStr = tfStockMin.getText().trim();
                
                if (descripcion.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || stockMinStr.isEmpty()) {
                    mostrarError("Debe completar todos los campos para actualizar el producto");
                    return;
                }
                
                double precio = Double.parseDouble(precioStr);
                int stock = Integer.parseInt(stockStr);
                int stockMin = Integer.parseInt(stockMinStr);
                
                if (precio <= 0 || stock < 0 || stockMin < 0) {
                    mostrarError("Los valores numéricos deben ser positivos");
                    return;
                }
                
                // Validación del stock mínimo
                if (stock < stockMin) {
                    int respuesta = JOptionPane.showConfirmDialog(
                        frame,
                        "⚠️ ADVERTENCIA ⚠️\n\n" +
                        "El nuevo stock (" + stock + ") es menor al stock mínimo (" + stockMin + ").\n" +
                        "Esto significa que el producto estará inmediatamente en estado de stock mínimo.\n\n" +
                        "¿Desea continuar de todas formas?",
                        "Stock por debajo del mínimo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (respuesta != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                // Remover el producto existente y agregar el actualizado
                catalogo.listarProductos().remove(productoExistente);
                Producto productoActualizado = new Producto(codigo, descripcion, precio, stock, stockMin);
                catalogo.cargarProducto(productoActualizado);
                
                areaProductos.append("╔════════════════════════════════════════════════╗\n");
                areaProductos.append("║          PRODUCTO ACTUALIZADO EXITOSAMENTE     ║\n");
                areaProductos.append("╠════════════════════════════════════════════════╣\n");
                areaProductos.append("ANTES: " + productoExistente + "\n");
                areaProductos.append("DESPUÉS: " + productoActualizado + "\n");
                if (stock < stockMin) {
                    areaProductos.append("⚠️  ATENCIÓN: Producto con stock por debajo del mínimo\n");
                }
                areaProductos.append("╚════════════════════════════════════════════════╝\n\n");
                limpiarCampos();
                
                mostrarExito("Producto actualizado exitosamente!");
                
            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese valores numéricos válidos");
            } catch (Exception ex) {
                mostrarError("Error al actualizar producto: " + ex.getMessage());
            }
        }
    }

    private static class MostrarStockMinimoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            areaProductos.append("\n╔═══════════════════════════════════════════════╗\n");
            areaProductos.append("║        PRODUCTOS CON STOCK MÍNIMO ALCANZADO   ║\n");
            areaProductos.append("╠═══════════════════════════════════════════════╣\n");
            
            List<Producto> productosStockMin = catalogo.marcarStockMinimo();
            
            if (productosStockMin.isEmpty()) {
                areaProductos.append("No hay productos con stock mínimo.\n");
                areaProductos.append("Todos los productos tienen stock suficiente.\n");
            } else {
                areaProductos.append("Se encontraron " + productosStockMin.size() + " producto(s):\n\n");
                for (Producto p : productosStockMin) {
                    areaProductos.append("⚠️  " + p + "\n");
                }
            }
            areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
        }
    }

    private static class ConsultarCantidadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int cantidad = catalogo.listarProductos().size();
            areaProductos.append("\n╔═══════════════════════════════════════════════╗\n");
            areaProductos.append("║                RESUMEN DEL CATÁLOGO            ║\n");
            areaProductos.append("╠═══════════════════════════════════════════════╣\n");
            areaProductos.append("Cantidad total de productos: " + cantidad + "\n");
            areaProductos.append("─────────────────────────────────────────────────\n");
            areaProductos.append("LISTA COMPLETA DE PRODUCTOS:\n");
            areaProductos.append("─────────────────────────────────────────────────\n");
            
            if (cantidad == 0) {
                areaProductos.append("El catálogo está vacío.\n");
                areaProductos.append("Agregue productos para comenzar.\n");
            } else {
                for (int i = 0; i < catalogo.listarProductos().size(); i++) {
                    Producto p = catalogo.listarProductos().get(i);
                    areaProductos.append((i + 1) + ". " + p + "\n");
                }
            }
            areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
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
                .filter(p -> codigo.isEmpty() || p.getCodigo().equals(codigo))
                .filter(p -> descripcion.isEmpty() || 
                    p.toString().toLowerCase().contains(descripcion.toLowerCase()))
                .filter(p -> filtrarPorPrecio(p, precioStr))
                .filter(p -> stockStr.isEmpty() || p.getCantidadStock() == Integer.parseInt(stockStr))
                .filter(p -> stockMinStr.isEmpty() || p.getStockMinimo() == Integer.parseInt(stockMinStr))
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

        private void mostrarResultadoBusqueda(List<Producto> filtrados) {
            areaProductos.append("\n╔═══════════════════════════════════════════════╗\n");
            areaProductos.append("║               RESULTADO DE BÚSQUEDA            ║\n");
            areaProductos.append("╠═══════════════════════════════════════════════╣\n");
            
            if (filtrados.isEmpty()) {
                areaProductos.append("No se encontraron productos con esos criterios.\n");
                areaProductos.append("Intente con diferentes parámetros de búsqueda.\n");
            } else {
                areaProductos.append("Se encontraron " + filtrados.size() + " producto(s):\n");
                areaProductos.append("─────────────────────────────────────────────────\n");
                for (int i = 0; i < filtrados.size(); i++) {
                    Producto p = filtrados.get(i);
                    areaProductos.append((i + 1) + ". " + p + "\n");
                }
            }
            areaProductos.append("╚═══════════════════════════════════════════════╝\n\n");
        }
    }
}