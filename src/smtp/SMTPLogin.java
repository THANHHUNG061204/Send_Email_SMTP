package smtp;

import javax.swing.*;
import java.awt.*;

public class SMTPLogin extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public SMTPLogin() {
        setTitle("Đăng nhập hệ thống SMTP");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("SMTP Client - Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 144, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Mật khẩu:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(30, 144, 255));
        btnLogin.setForeground(Color.white);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel);

        // Xử lý login
        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                dispose(); // đóng login
                new SMTPClientGUI().setVisible(true); // mở GUI gửi mail
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SMTPLogin().setVisible(true));
    }
}
