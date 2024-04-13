package intentotarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Login extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contraseñaField;
    private Map<String, String> usuarios;

    public Login(Map<String, String> usuarios) {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        this.usuarios = usuarios;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField(10);

        JLabel contraseñaLabel = new JLabel("Contraseña:");
        contraseñaField = new JPasswordField(10);

        JButton ingresarButton = new JButton("Ingresar");
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Capturar usuario y contraseña ingresados
                String usuario = usuarioField.getText();
                String contraseña = new String(contraseñaField.getPassword());

                // Validar que no se ingresen campos vacíos
                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Por favor, ingrese usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Encriptar la contraseña ingresada para compararla con la guardada
                String contraseñaEncriptada = encriptarContraseña(contraseña);

                // Verificar si el usuario existe y si la contraseña encriptada coincide
                if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contraseñaEncriptada)) {
                    JOptionPane.showMessageDialog(Login.this, "Usuario y contraseña son correctos.");

                    // Cerrar las ventanas de IntentoTarea y Login
                    dispose();

                    // Abrir RegistroIMCApp
                    abrirRegistroIMCApp();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario y contraseña son incorrectos.");
                }

                // Limpiar campos
                usuarioField.setText("");
                contraseñaField.setText("");
            }
        });

        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(contraseñaLabel);
        panel.add(contraseñaField);
        panel.add(ingresarButton);

        add(panel);
    }

    private String encriptarContraseña(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(contraseña.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void abrirRegistroIMCApp() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegistroIMCApp().frame.setVisible(true);
            }
        });
    }
}



