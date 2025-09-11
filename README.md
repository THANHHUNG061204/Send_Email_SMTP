<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>
<h2 align="center">
   Gửi Email thông qua SMTP
</h2>
<div align="center">
    <p align="center">
        <img src="docs/aiotlab_logo.png" alt="AIoTLab Logo" width="170"/>
        <img src="docs/fitdnu_logo.png" alt="AIoTLab Logo" width="180"/>
        <img src="docs/dnu_logo.png" alt="DaiNam University Logo" width="200"/>
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

</div>

## 📖 1. Giới thiệu
SMTP (Simple Mail Transfer Protocol) là giao thức chuẩn được sử dụng để truyền tải email qua Internet. Khi người dùng soạn và gửi một email, ứng dụng (như Outlook, Gmail, hay hệ thống phần mềm doanh nghiệp) sẽ dùng SMTP để gửi thư từ máy khách (client) tới máy chủ (mail server), và từ đó phân phối đến máy chủ đích.

Quy trình gửi email qua SMTP thường gồm:

1.Xác thực người gửi: Máy khách đăng nhập vào máy chủ SMTP bằng tài khoản hợp lệ.

2.Truyền tải thông điệp: Email được đóng gói và gửi qua các lệnh SMTP (HELO, MAIL FROM, RCPT TO, DATA...).

3.Chuyển tiếp và phân phối: Máy chủ SMTP trung gian chuyển tiếp đến máy chủ nhận, nơi người nhận có thể truy cập email qua POP3 hoặc IMAP.
## 🔧 2. Ngôn ngữ lập trình sử dụng: [![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)

## 🚀 3. Công nghệ sử dụng
1.Ngôn ngữ Java – lập trình hướng đối tượng, hỗ trợ socket, dễ xây dựng mô phỏng SMTP.

2.Eclipse IDE – công cụ phát triển Java phổ biến, hỗ trợ soạn thảo, chạy và debug code.

3.Socket TCP/IP – dùng ServerSocket và Socket để mô phỏng giao thức SMTP.

4.Swing (Java GUI) – xây dựng giao diện trực quan (JFrame, JTextField, JTextArea, JButton).

## 📝 4. Hình ảnh chức năng
<img width="726" height="610" alt="{3C51E417-8633-460F-BCB1-DA97782B65DB}" src="https://github.com/user-attachments/assets/60e2ec0a-b959-447b-aabc-8e43b4e71b98" />

## 5. Các bước cài đặt
### 1. Bước 1:File → New → Java Project → nhập tên project (ví dụ: SMTP_Socket) sau đó tạo package smtp.
### 2. Bước 2:Tạo các class: SMTPServer.java, SMTPClient.java, SMTPClientGUI.java sau đó dán code vào.
### 3. Bước 3:Chạy SMTP.sever trước để mở cổng lắng nghe sau đó chạy SMTPClientGUI.java để gửi mail mô phỏng.
