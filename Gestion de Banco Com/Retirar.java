import java.awt.*;
import javax.swing.*;

public class Retirar extends JFrame {

    public Retirar() {
        setTitle("Retirar");
        setSize(420, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 6, 6));

        add(new JLabel("Numero de cuenta:"));
        JTextField tfNumero = new JTextField();
        add(tfNumero);

        add(new JLabel("Monto a retirar:"));
        JTextField tfMonto = new JTextField();
        add(tfMonto);

        JButton btnRetirar = new JButton("Retirar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnRetirar);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnRetirar.addActionListener(e -> {
            String num = tfNumero.getText().trim();
            double monto;
            try { 
                monto = Double.parseDouble(tfMonto.getText().trim()); 
            }catch (NumberFormatException ex) { 
                JOptionPane.showMessageDialog(this, "Monto invalido.");
                 return; 
                }

            Cuenta c = GestorCuentas.buscar(num);
            if (c == null) { 
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada."); 
                return; 
            }
            if (c.isCerrada()) { 
                JOptionPane.showMessageDialog(this, "Cuenta está cerrada."); 
                return; 
            }
            if (c.getSaldo() < monto) { 
                JOptionPane.showMessageDialog(this, "Saldo insuficiente."); 
                return; 
            }

            int conf = JOptionPane.showConfirmDialog(this, "¿Retirar $" + monto + " de " + num + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (GestorCuentas.retirar(num, monto)) {
                    JOptionPane.showMessageDialog(this, "Retiro realizado.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo retirar.");
                }
            }
        });

        setVisible(true);
    }
}
