import java.awt.*;
import javax.swing.*;

public class ModificarNombre extends JFrame {

    public ModificarNombre() {
        setTitle("Modificar Nombre");
        setSize(480, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 6, 6));

        add(new JLabel("Numero de cuenta:"));
        JTextField tfNumero = new JTextField();
        add(tfNumero);

        add(new JLabel("Nuevo nombre:"));
        JTextField tfNuevo = new JTextField();
        add(tfNuevo);

        JButton btnModificar = new JButton("Modificar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnModificar);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnModificar.addActionListener(e -> {
            String num = tfNumero.getText().trim();
            String nuevo = tfNuevo.getText().trim();
            if (num.isEmpty() || nuevo.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Complete los datos."); 
                 return; 
                }

            Cuenta c = GestorCuentas.buscar(num);
            if (c == null) { 
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada."); 
                return; 
            }
            if (c.isCerrada()) { 
                JOptionPane.showMessageDialog(this, "Cuenta esta cerrada."); 
                return;
             }

            int conf = JOptionPane.showConfirmDialog(this, "¿Modificar nombre de " + num + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (GestorCuentas.modificarNombre(num, nuevo)) {
                    JOptionPane.showMessageDialog(this, "Nombre actualizado.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar.");
                }
            }
        });

        setVisible(true);
    }
}
