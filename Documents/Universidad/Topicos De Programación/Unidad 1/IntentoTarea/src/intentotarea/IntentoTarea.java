package intentotarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class IntentoTarea extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contraseñaField;
    private Map<String, String> usuarios;

    public IntentoTarea() {
        super("Ingreso de Contraseña para Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        usuarios = new HashMap<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField(10);

        JLabel contraseñaLabel = new JLabel("Contraseña:");
        contraseñaField = new JPasswordField(10);

        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Capturar usuario y contraseña ingresados
                String usuario = usuarioField.getText();
                String contraseña = new String(contraseñaField.getPassword());

                // Validar que no se ingresen campos vacíos
                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(IntentoTarea.this, "Por favor, ingrese usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Encriptar la contraseña
                String contraseñaEncriptada = encriptarContraseña(contraseña);

                // Agregar usuario y contraseña encriptada a la lista de usuarios
                usuarios.put(usuario, contraseñaEncriptada);

                // Limpiar campos
                usuarioField.setText("");
                contraseñaField.setText("");

                JOptionPane.showMessageDialog(IntentoTarea.this, "Usuario y contraseña guardados en memoria.");
            }
        });

        JButton salirButton = new JButton("Salir");
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(contraseñaLabel);
        panel.add(contraseñaField);
        panel.add(aceptarButton);
        panel.add(salirButton);

        add(panel);
    }

    // Método para obtener la lista de usuarios encriptados
    public Map<String, String> getUsuarios() {
        return usuarios;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                IntentoTarea intentoTarea = new IntentoTarea();
                intentoTarea.setVisible(true);

                new Login(intentoTarea.getUsuarios()).setVisible(true);
            }
        });
    }
}
