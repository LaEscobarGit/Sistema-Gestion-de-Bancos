import java.awt.*;
import javax.swing.*;

public class Depositar extends JFrame {

    public Depositar() {
        setTitle("Depositar");
        setSize(420, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 6, 6));

        add(new JLabel("Numero de cuenta:"));
        JTextField tfNumero = new JTextField();
        add(tfNumero);

        add(new JLabel("Monto a depositar:"));
        JTextField tfMonto = new JTextField();
        add(tfMonto);

        JButton btnDepositar = new JButton("Depositar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnDepositar);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnDepositar.addActionListener(e -> {
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
                JOptionPane.showMessageDialog(this, "Cuenta esta cerrada.");
                 return;
                 }

            int conf = JOptionPane.showConfirmDialog(this, "¿Depositar $" + monto + " a " + num + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (GestorCuentas.depositar(num, monto)) {
                    JOptionPane.showMessageDialog(this, "Deposito realizado.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo depositar.");
                }
            }
        });

        setVisible(true);
    }
}
