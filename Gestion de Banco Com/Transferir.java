import java.awt.*;
import javax.swing.*;

public class Transferir extends JFrame {

    public Transferir() {
        setTitle("Transferir");
        setSize(480, 220);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 6, 6));

        add(new JLabel("Cuenta origen:"));
        JTextField tfOrigen = new JTextField();
        add(tfOrigen);

        add(new JLabel("Cuenta destino:"));
        JTextField tfDestino = new JTextField();
        add(tfDestino);

        add(new JLabel("Monto:"));
        JTextField tfMonto = new JTextField();
        add(tfMonto);

        JButton btnTransferir = new JButton("Transferir");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnTransferir);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnTransferir.addActionListener(e -> {
            String origen = tfOrigen.getText().trim();
            String destino = tfDestino.getText().trim();
            double monto;
            if (origen.isEmpty() || destino.isEmpty()) { 
                JOptionPane.showMessageDialog(this, "Complete las cuentas."); 
                return; 
            }
            if (origen.equals(destino)) { 
                JOptionPane.showMessageDialog(this, "Cuentas no pueden ser iguales."); 
                return; 
            }
            try { 
                monto = Double.parseDouble(tfMonto.getText().trim()); 
            }catch (NumberFormatException ex) { 
                JOptionPane.showMessageDialog(this, "Monto invalido."); 
                return; 
            }

            Cuenta cO = GestorCuentas.buscar(origen);
            Cuenta cD = GestorCuentas.buscar(destino);
            if (cO == null) { 
                JOptionPane.showMessageDialog(this, "Cuenta origen no encontrada."); 
                return; 
            }
            if (cD == null) {
                 JOptionPane.showMessageDialog(this, "Cuenta destino no encontrada."); 
                 return;
                 }
            if (cO.isCerrada() || cD.isCerrada()) { 
                JOptionPane.showMessageDialog(this, "Alguna cuenta estq cerrada."); 
                return; 
            }
            if (cO.getSaldo() < monto) { 
                JOptionPane.showMessageDialog(this, "Saldo insuficiente en origen."); 
                return;
             }

            int conf = JOptionPane.showConfirmDialog(this, "¿Transferir $" + monto + " de " + origen + " a " + destino + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (GestorCuentas.transferir(origen, destino, monto)) {
                    JOptionPane.showMessageDialog(this, "Transferencia realizada.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo realizar la transferencia.");
                }
            }
        });

        setVisible(true);
    }
}
