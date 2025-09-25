package smtp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64;

public class SMTPServer {
    private static final String CRLF = "\r\n";
    private int port = 2526;
    private File mailboxDir;

    public SMTPServer(int port, String mailboxPath) {
        this.port = port;
        this.mailboxDir = new File(mailboxPath);
        if (!mailboxDir.exists()) mailboxDir.mkdirs();
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[SERVER] Listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private BufferedWriter writer;
        private String mailFrom;
        private List<String> rcptTo;
        private boolean dataMode;
        private List<String> buffer;
        private boolean authenticated = false;

        // Tài khoản hợp lệ
        private final String validUser = "user";
        private final String validPass = "123";

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.rcptTo = new ArrayList<>();
            this.buffer = new ArrayList<>();
            this.dataMode = false;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

                sendLine("220 SimpleSMTP ready");

                String line;
                while ((line = reader.readLine()) != null) {
                    if (dataMode) {
                        if (line.equals(".")) {
                            saveMessage();
                            sendLine("250 Message accepted for delivery");
                            reset();
                            dataMode = false;
                        } else {
                            buffer.add(line);
                        }
                        continue;
                    }

                    String[] parts = line.split(" ", 2);
                    String cmd = parts[0].toUpperCase(Locale.ROOT);
                    String arg = parts.length > 1 ? parts[1] : "";

                    switch (cmd) {
                        case "HELO":
                        case "EHLO":
                            sendLine("250 Hello " + arg);
                            break;

                        case "AUTH":
                            if (arg.toUpperCase().startsWith("LOGIN")) {
                                sendLine("334 VXNlcm5hbWU6"); // "Username:"
                                String username = reader.readLine();
                                sendLine("334 UGFzc3dvcmQ6"); // "Password:"
                                String password = reader.readLine();

                                String decodedUser = new String(Base64.getDecoder().decode(username), StandardCharsets.UTF_8);
                                String decodedPass = new String(Base64.getDecoder().decode(password), StandardCharsets.UTF_8);

                                if (decodedUser.equals(validUser) && decodedPass.equals(validPass)) {
                                    authenticated = true;
                                    sendLine("235 Authentication successful");
                                } else {
                                    sendLine("535 Authentication failed");
                                }
                            } else {
                                sendLine("504 Unrecognized authentication type");
                            }
                            break;

                        case "MAIL":
                            if (!authenticated) {
                                sendLine("530 Authentication required");
                            } else if (arg.toUpperCase().startsWith("FROM:")) {
                                mailFrom = extractAddr(arg.substring(5).trim());
                                sendLine("250 OK");
                            } else {
                                sendLine("501 Syntax: MAIL FROM:<address>");
                            }
                            break;

                        case "RCPT":
                            if (!authenticated) {
                                sendLine("530 Authentication required");
                            } else if (arg.toUpperCase().startsWith("TO:")) {
                                String rcpt = extractAddr(arg.substring(3).trim());
                                rcptTo.add(rcpt);
                                sendLine("250 OK");
                            } else {
                                sendLine("501 Syntax: RCPT TO:<address>");
                            }
                            break;

                        case "DATA":
                            if (!authenticated) {
                                sendLine("530 Authentication required");
                            } else if (mailFrom == null || rcptTo.isEmpty()) {
                                sendLine("503 Bad sequence of commands");
                            } else {
                                sendLine("354 End data with <CRLF>.<CRLF>");
                                dataMode = true;
                                buffer.clear();
                            }
                            break;

                        case "RSET":
                            reset();
                            sendLine("250 OK");
                            break;
                        case "NOOP":
                            sendLine("250 OK");
                            break;
                        case "QUIT":
                            sendLine("221 Bye");
                            socket.close();
                            return;
                        default:
                            sendLine("502 Command not implemented");
                    }
                }
            } catch (Exception e) {
                System.err.println("[SERVER] Error: " + e.getMessage());
            }
        }

        private void sendLine(String response) throws IOException {
            writer.write(response + CRLF);
            writer.flush();
        }

        private void reset() {
            mailFrom = null;
            rcptTo.clear();
            buffer.clear();
        }

        private void saveMessage() throws IOException {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File msgFile = new File(mailboxDir, "msg_" + timestamp + ".eml");
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(msgFile), StandardCharsets.UTF_8))) {
                for (String l : buffer) {
                    out.println(l);
                }
            }
            System.out.println("[SERVER] Saved message: " + msgFile.getAbsolutePath());
        }

        private String extractAddr(String s) {
            if (s.startsWith("<") && s.endsWith(">")) {
                return s.substring(1, s.length() - 1);
            }
            return s;
        }
    }

    public static void main(String[] args) throws IOException {
        SMTPServer server = new SMTPServer(2526, "mailbox");
        server.start();
    }
}
