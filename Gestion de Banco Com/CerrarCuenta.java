import java.awt.*;
import javax.swing.*;

public class CerrarCuenta extends JFrame {

    public CerrarCuenta() {
        setTitle("Cerrar Cuenta");
        setSize(420, 160);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 6, 6));

        add(new JLabel("Número de cuenta:"));
        JTextField tfNumero = new JTextField();
        add(tfNumero);

        JButton btnCerrar = new JButton("Cerrar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnCerrar);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnCerrar.addActionListener(e -> {
            String num = tfNumero.getText().trim();
            if (num.isEmpty()) { 
                JOptionPane.showMessageDialog(this, "Ingrese numero.");
                 return; 
                }

            Cuenta c = GestorCuentas.buscar(num);
            if (c == null) { 
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada."); 
                return; 
            }
            if (c.getSaldo() != 0) { 
                JOptionPane.showMessageDialog(this, "Saldo debe ser 0."); 
                return;
             }

            int conf = JOptionPane.showConfirmDialog(this, "¿Cerrar la cuenta " + num + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (GestorCuentas.cerrarCuenta(num)) {
                    JOptionPane.showMessageDialog(this, "Cuenta cerrada.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo cerrar la cuenta.");
                }
            }
        });

        setVisible(true);
    }
}
