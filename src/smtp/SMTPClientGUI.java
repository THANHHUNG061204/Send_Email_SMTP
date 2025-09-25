package smtp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;

public class SMTPClientGUI extends JFrame {
    private JTextField txtFrom, txtTo, txtCc, txtBcc, txtSubject;
    private JTextArea txtBody;
    private JLabel lblAttachment;
    private File attachedFile;

    public SMTPClientGUI() {
        setTitle("📧 Gmail Mini Client - Compose");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ======= HEADER =======
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 191, 255),
                        getWidth(), getHeight(), new Color(123, 104, 238));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(950, 60));
        JLabel lblTitle = new JLabel(" Gmail Mini Client");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle);

        // ======= Compose Panel =======
        JPanel composePanel = buildComposePanel();

        // Layout
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(composePanel, BorderLayout.CENTER);
    }

    // ========== COMPOSE PANEL ==========
    private JPanel buildComposePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFrom = new JTextField("me@example.com");
        txtTo = new JTextField();
        txtCc = new JTextField();
        txtBcc = new JTextField();
        txtSubject = new JTextField();

        String[] labels = {"From:", "To:", "CC:", "BCC:", "Subject:"};
        JTextField[] fields = {txtFrom, txtTo, txtCc, txtBcc, txtSubject};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            formPanel.add(lbl, gbc);

            gbc.gridx = 1; gbc.gridy = i; gbc.weightx = 1.0; gbc.gridwidth = 2;
            fields[i].setBorder(new LineBorder(new Color(180, 180, 255), 1, true));
            formPanel.add(fields[i], gbc);
        }

        // Body
        txtBody = new JTextArea();
        txtBody.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtBody.setForeground(new Color(40, 40, 40));
        txtBody.setBackground(new Color(250, 250, 255));
        txtBody.setLineWrap(true);
        txtBody.setWrapStyleWord(true);
        JScrollPane bodyScroll = new JScrollPane(txtBody);
        bodyScroll.setBorder(BorderFactory.createTitledBorder("✏ Nội dung email"));

        // Attachment
        JPanel attachPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAttach = new JButton("📎 Đính kèm");
        lblAttachment = new JLabel("Chưa có tệp đính kèm");
        attachPanel.add(btnAttach);
        attachPanel.add(lblAttachment);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnSend = fancyButton(" Gửi", new Color(72, 209, 204), new Color(0, 191, 255));
        JButton btnDraft = fancyButton(" Lưu nháp", new Color(255, 165, 0), new Color(255, 99, 71));
        JButton btnPreview = fancyButton(" Xem trước", new Color(144, 238, 144), new Color(60, 179, 113));
        JButton btnClear = fancyButton(" Xóa", new Color(255, 69, 0), new Color(220, 20, 60));
        JButton btnSentBox = fancyButton(" Xem Sent Mailbox", new Color(100, 149, 237), new Color(65, 105, 225));

        buttonPanel.add(btnSentBox);
        buttonPanel.add(btnPreview);
        buttonPanel.add(btnDraft);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSend);

        // Action attach
        btnAttach.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                attachedFile = fc.getSelectedFile();
                lblAttachment.setText("📎 " + attachedFile.getName());
            }
        });

        // Action send (liên kết SentMailbox)
        btnSend.addActionListener(e -> {
            SentMailbox.saveSentMail(
                    txtFrom.getText(),
                    txtTo.getText(),
                    txtSubject.getText(),
                    txtBody.getText()
            );
            JOptionPane.showMessageDialog(this,
                    "📤 Email gửi thành công và đã lưu vào Sent Mailbox!",
                    "Gửi thành công", JOptionPane.INFORMATION_MESSAGE);
        });

        btnDraft.addActionListener(e -> JOptionPane.showMessageDialog(this, " Email đã lưu nháp!"));
        btnPreview.addActionListener(e -> JOptionPane.showMessageDialog(this, txtBody.getText(),
                "👀 Xem trước", JOptionPane.INFORMATION_MESSAGE));
        btnClear.addActionListener(e -> {
            txtSubject.setText(""); txtBody.setText("");
            txtTo.setText(""); txtCc.setText(""); txtBcc.setText("");
            lblAttachment.setText("Chưa có tệp đính kèm");
        });

        btnSentBox.addActionListener(e -> new SentMailbox().setVisible(true));

        // Layout
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(bodyScroll, BorderLayout.CENTER);
        panel.add(attachPanel, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.PAGE_END);

        return panel;
    }

    // ========== BUTTON CUSTOM ==========
    private JButton fancyButton(String text, Color c1, Color c2) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SMTPClientGUI().setVisible(true));
    }
}
