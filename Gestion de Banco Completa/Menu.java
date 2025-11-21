import java.awt.*;
import javax.swing.*;

public class Menu extends JFrame {

    public Menu() {

        setTitle("Gestión de Cuentas Bancarias");
        setSize(1024, 576);
        setLocationRelativeTo(null); // Centrado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Permitir redimensionar

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Botones del sistema
        JButton btnCrear = new JButton("Crear Cuenta");
        JButton btnCerrar = new JButton("Cerrar Cuenta");
        JButton btnModificar = new JButton("Modificar Nombre");
        JButton btnDepositar = new JButton("Depositar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnTransferir = new JButton("Transferir");

        panel.add(btnCrear);
        panel.add(btnCerrar);
        panel.add(btnModificar);
        panel.add(btnDepositar);
        panel.add(btnRetirar);
        panel.add(btnTransferir);

        add(panel);

        // Acciones
        btnCrear.addActionListener(e -> new CrearCuenta());
        btnCerrar.addActionListener(e -> new CerrarCuenta());
        btnModificar.addActionListener(e -> new ModificarNombre());
        btnDepositar.addActionListener(e -> new Depositar());
        btnRetirar.addActionListener(e -> new Retirar());
        btnTransferir.addActionListener(e -> new Transferir());
    }

    public static void main(String[] args) {
        new Menu().setVisible(true);
        Login login = new Login();
        login.setTitle("Inicio de Sesión");
        login.setSize(1024, 576);
        login.setLocationRelativeTo(null);
        login.setResizable(false);
        login.setVisible(true);
    }
}
