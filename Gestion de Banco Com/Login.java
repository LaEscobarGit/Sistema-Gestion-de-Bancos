import javax.swing.*;

public class Login extends JFrame {

    public Login() {

        setTitle("Iniciar Sesión");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblContrasena = new JLabel("Contraseña:");

        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();

        JButton btnIngresar = new JButton("Ingresar");

        lblUsuario.setBounds(40, 40, 100, 30);
        txtUsuario.setBounds(150, 40, 180, 30);

        lblContrasena.setBounds(40, 90, 100, 30);
        txtContrasena.setBounds(150, 90, 180, 30);

        btnIngresar.setBounds(140, 150, 120, 30);

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(btnIngresar);

        add(panel);

        // Acción del botón
        btnIngresar.addActionListener(e -> {

            String user = txtUsuario.getText();
            String pass = new String(txtContrasena.getPassword());

            // Credenciales simples
            if (user.equals("admin") && pass.equals("1234")) {

                JOptionPane.showMessageDialog(this, "Bienvenido " + user);

                // Abrir el menú principal
                new Menu().setVisible(true);

                // Cerrar login
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
