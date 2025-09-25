package smtp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SMTPClientGUI extends JFrame {
    private JTextField txtFrom, txtTo, txtSubject;
    private JTextArea txtBody;
    private JButton btnSend;

    public SMTPClientGUI() {
        setTitle("SMTP Client - Gửi Email");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("From:"));
        txtFrom = new JTextField("demo@example.com");
        formPanel.add(txtFrom);

        formPanel.add(new JLabel("To:"));
        txtTo = new JTextField("alice@example.com,bob@example.com");
        formPanel.add(txtTo);

        formPanel.add(new JLabel("Subject:"));
        txtSubject = new JTextField("Hello from Java");
        formPanel.add(txtSubject);

        txtBody = new JTextArea("Xin chào!\nĐây là email thử nghiệm từ client Java.");
        txtBody.setLineWrap(true);
        txtBody.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtBody);

        btnSend = new JButton("Gửi Email");
        btnSend.setBackground(new Color(50, 205, 50));
        btnSend.setForeground(Color.white);
        btnSend.setFont(new Font("Arial", Font.BOLD, 14));

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnSend, BorderLayout.SOUTH);

        btnSend.addActionListener(e -> sendEmail());
    }

    private void sendEmail() {
        try {
            Socket socket = new Socket("127.0.0.1", 2526);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            reader.readLine();
            writer.write("HELO client.local\r\n"); writer.flush();
            reader.readLine();

            writer.write("MAIL FROM:<" + txtFrom.getText() + ">\r\n"); writer.flush();
            reader.readLine();

            for (String rcpt : txtTo.getText().split(",")) {
                writer.write("RCPT TO:<" + rcpt.trim() + ">\r\n"); writer.flush();
                reader.readLine();
            }

            writer.write("DATA\r\n"); writer.flush();
            reader.readLine();

            writer.write("Subject: " + txtSubject.getText() + "\r\n");
            writer.write(txtBody.getText() + "\r\n.\r\n");
            writer.flush();
            reader.readLine();

            writer.write("QUIT\r\n"); writer.flush();
            reader.readLine();

            JOptionPane.showMessageDialog(this, "Email đã gửi thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi gửi email: " + ex.getMessage());
        }
    }
}
