package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UtilidadesSwing {
    public static JTextField crearTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBackground(App.COLOR_FONDO_SECUNDARIO);
        textField.setForeground(App.COLOR_TEXTO);
        textField.setCaretColor(App.COLOR_ACENTO);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(App.COLOR_ACENTO, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(App.COLOR_ACENTO_HOVER, 2),
                    BorderFactory.createEmptyBorder(4, 7, 4, 7)
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!textField.hasFocus()) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(App.COLOR_ACENTO, 1),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                    ));
                }
            }
        });
        return textField;
    }

    public static JComboBox<String> crearComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBackground(App.COLOR_FONDO_SECUNDARIO);
        comboBox.setForeground(App.COLOR_TEXTO);
        comboBox.setBorder(BorderFactory.createLineBorder(App.COLOR_ACENTO, 1));
        // Renderer personalizado para asegurar visibilidad
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? App.COLOR_ACENTO : App.COLOR_FONDO_SECUNDARIO);
                label.setForeground(App.COLOR_TEXTO);
                return label;
            }
        });
        return comboBox;
    }

    public static JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(App.COLOR_TEXTO);
        return label;
    }

    public static JButton crearBotonModerno(String texto, Color colorBase) {
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
        return boton;
    }
}