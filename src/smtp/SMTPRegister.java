package smtp;

import javax.swing.*;
import java.awt.*;

public class SMTPRegister extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword, txtConfirm;
    private JButton btnRegister, btnBack;

    public SMTPRegister() {
        setTitle("Đăng ký tài khoản SMTP");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 250, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("SMTP Client - Register");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 128, 0));
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

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        txtConfirm = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(txtConfirm, gbc);

        btnRegister = new JButton("Đăng ký");
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setForeground(Color.white);

        btnBack = new JButton("Quay lại");
        btnBack.setBackground(new Color(100, 149, 237));
        btnBack.setForeground(Color.white);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        panel.add(btnRegister, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(btnBack, gbc);

        add(panel);

        // Xử lý nút
        btnRegister.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirm.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            } else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công cho user: " + user);
                dispose();
                new SMTPLogin().setVisible(true); // quay lại login
            }
        });

        btnBack.addActionListener(e -> {
            dispose();
            new SMTPLogin().setVisible(true); // quay lại login
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SMTPRegister().setVisible(true));
    }
}
