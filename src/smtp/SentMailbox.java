package smtp;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SentMailbox extends JFrame {
    private JTable table;
    private JTextArea detailArea;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public SentMailbox() {
        setTitle("📂 Sent Mailbox");
        setSize(1000, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // ======= HEADER =======
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(65, 105, 225),
                        getWidth(), getHeight(), new Color(138, 43, 226));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(1000, 60));
        JLabel lblTitle = new JLabel(" Sent Mailbox - Hộp thư đã gửi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle);

        // ======= BẢNG DANH SÁCH MAIL =======
        String[] columns = {"File", "From", "To", "Subject", "Time"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 15));
        th.setBackground(new Color(70, 130, 180));
        th.setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(table);

        // ======= PANEL TÌM KIẾM + REFRESH =======
        JPanel topPanel = new JPanel(new BorderLayout());

        // Ô tìm kiếm có placeholder
        searchField = new JTextField(25) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.GRAY);
                    g2.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                    g2.drawString(" Nhập từ khóa...", 5, getHeight() - 8);
                    g2.dispose();
                }
            }
        };
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JButton btnSearch = gradientButton(" Tìm", new Color(72, 61, 139), new Color(123, 104, 238));
        JButton btnRefresh = gradientButton(" Refresh", new Color(0, 191, 255), new Color(65, 105, 225));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.add(btnRefresh);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(btnPanel, BorderLayout.EAST);

        // ======= KHUNG CHI TIẾT =======
        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        detailArea.setBackground(new Color(250, 250, 250));
        JScrollPane detailScroll = new JScrollPane(detailArea);

        // ======= CHIA ĐÔI GIAO DIỆN =======
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, detailScroll);
        splitPane.setDividerLocation(300);

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(splitPane, BorderLayout.SOUTH);

        // ======= SỰ KIỆN =======
        btnRefresh.addActionListener(e -> loadSentMail(null));
        btnSearch.addActionListener(e -> loadSentMail(searchField.getText().trim()));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                String fileName = (String) table.getValueAt(table.getSelectedRow(), 0);
                File f = new File("sent", fileName);
                try {
                    String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
                    detailArea.setText(content);
                } catch (Exception ex) {
                    detailArea.setText("❌ Lỗi đọc mail: " + ex.getMessage());
                }
            }
        });

        // Load dữ liệu ban đầu
        loadSentMail(null);
    }

    // ======= NÚT ĐẸP =======
    private JButton gradientButton(String text, Color c1, Color c2) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    // ======= LOAD MAIL =======
    private void loadSentMail(String keyword) {
        tableModel.setRowCount(0);
        File sentDir = new File("sent");
        if (sentDir.exists() && sentDir.isDirectory()) {
            for (File f : sentDir.listFiles()) {
                try {
                    List<String> lines = java.nio.file.Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
                    String from = lines.stream().filter(l -> l.startsWith("From:")).findFirst().orElse("?");
                    String to = lines.stream().filter(l -> l.startsWith("To:")).findFirst().orElse("?");
                    String subject = lines.stream().filter(l -> l.startsWith("Subject:")).findFirst().orElse("?");
                    String time = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(f.lastModified()));

                    if (keyword == null || subject.toLowerCase().contains(keyword.toLowerCase())
                            || to.toLowerCase().contains(keyword.toLowerCase())) {
                        tableModel.addRow(new Object[]{
                                f.getName(),
                                from.replace("From: ", ""),
                                to.replace("To: ", ""),
                                subject.replace("Subject: ", ""),
                                time});
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // ======= HÀM TIỆN ÍCH: LƯU MAIL =======
    public static void saveSentMail(String from, String to, String subject, String body) {
        try {
            File dir = new File("sent");
            if (!dir.exists()) dir.mkdirs();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File f = new File(dir, "sent_" + timestamp + ".eml");
            try (PrintWriter out = new PrintWriter(f, "UTF-8")) {
                out.println("From: " + from);
                out.println("To: " + to);
                out.println("Subject: " + subject);
                out.println("Date: " + new Date());
                out.println();
                out.println(body);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Test độc lập
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SentMailbox().setVisible(true));
    }
}
