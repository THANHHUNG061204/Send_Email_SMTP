package smtp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SMTPClient {
    private static final String CRLF = "\r\n";
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SMTPClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    private void sendLine(String line) throws IOException {
        writer.write(line + CRLF);
        writer.flush();
    }

    private String recvLine() throws IOException {
        return reader.readLine();
    }

    public void sendMail(String from, List<String> to, String subject, String body) throws IOException {
        System.out.println("[SERVER] " + recvLine());

        sendLine("HELO client.local");
        System.out.println("[SERVER] " + recvLine());

        sendLine("MAIL FROM:<" + from + ">");
        System.out.println("[SERVER] " + recvLine());

        for (String rcpt : to) {
            sendLine("RCPT TO:<" + rcpt + ">");
            System.out.println("[SERVER] " + recvLine());
        }

        sendLine("DATA");
        System.out.println("[SERVER] " + recvLine());

        writer.write("Subject: " + subject + CRLF);
        writer.write(body + CRLF + "." + CRLF);
        writer.flush();
        System.out.println("[SERVER] " + recvLine());

        sendLine("QUIT");
        System.out.println("[SERVER] " + recvLine());
    }

    public static void main(String[] args) {
        try {
            SMTPClient client = new SMTPClient("127.0.0.1", 2525);
            client.sendMail(
                "demo@example.com",
                Arrays.asList("alice@example.com", "bob@example.com"),
                "Hello from Java SMTP Client",
                "Xin chào!\nĐây là email thử nghiệm từ client Java."
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
