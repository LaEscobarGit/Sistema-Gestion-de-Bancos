import java.awt.*;
import javax.swing.*;

public class CrearCuenta extends JFrame {

    public CrearCuenta() {
        setTitle("Crear Cuenta");
        setSize(420, 240);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 6, 6));

        add(new JLabel("Nombre:"));
        JTextField tfNombre = new JTextField();
        add(tfNombre);

        add(new JLabel("Saldo inicial:"));
        JTextField tfSaldo = new JTextField("0");
        add(tfSaldo);

        JButton btnCrear = new JButton("Crear");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnCrear);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnCrear.addActionListener(e -> {
            String nom = tfNombre.getText().trim();
            double saldo;
            if (nom.isEmpty()) { 
                JOptionPane.showMessageDialog(this, "Ingrese el nombre.");
                 return;
                 }
            try { 
                saldo = Double.parseDouble(tfSaldo.getText().trim()); 
            }
            catch (NumberFormatException ex) { 
                JOptionPane.showMessageDialog(this, "Saldo invalido."); 
                return;
             }

            String[] salida = new String[1];
            boolean ok = GestorCuentas.crearCuentaAuto(nom, saldo, salida);
            if (ok) {
                String id = salida[0] != null ? salida[0] : "Desconocido";
                JOptionPane.showMessageDialog(this, "Cuenta creada con ID: " + id);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo crear la cuenta (limite 10 o ya existe).");
            }
        });

        setVisible(true);
    }
}
