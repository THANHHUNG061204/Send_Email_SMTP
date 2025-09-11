<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>
<h2 align="center">
   Send Email STMP
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
1.Ngôn ngữ lập trình Java

Java là ngôn ngữ lập trình hướng đối tượng phổ biến, mạnh mẽ, độc lập nền tảng.

Java có thư viện hỗ trợ mạng (java.net), cho phép lập trình socket dễ dàng để mô phỏng giao thức SMTP.

Ưu điểm: ổn định, đa nền tảng (Windows, Linux, macOS), dễ tích hợp với giao diện đồ họa (Swing, JavaFX).

2.Môi trường phát triển tích hợp (IDE) – Eclipse

Eclipse là IDE mã nguồn mở, phổ biến cho lập trình Java.

Các tính năng chính:

Soạn thảo code Java với gợi ý cú pháp, kiểm tra lỗi biên dịch ngay khi gõ.

Tích hợp quản lý project, package, class thuận tiện.

Hỗ trợ chạy, debug chương trình socket (Server/Client) trực tiếp.

Cho phép tạo giao diện người dùng với Swing hoặc JavaFX dễ dàng.

Hỗ trợ nhiều plugin mở rộng (Git, Maven, Gradle) phục vụ phát triển phần mềm.

3.Công nghệ Socket trong Java

Sử dụng ServerSocket để lắng nghe và chấp nhận kết nối từ client.

Sử dụng Socket để tạo kết nối client–server và truyền dữ liệu theo chuẩn TCP.

Dữ liệu được trao đổi bằng InputStream và OutputStream qua kết nối socket.

Đây là nền tảng để mô phỏng giao thức SMTP (HELO, MAIL FROM, RCPT TO, DATA, QUIT).

4.Thư viện giao diện người dùng Swing (nếu có GUI)

Swing là bộ thư viện GUI tích hợp sẵn trong Java.

Cho phép xây dựng giao diện trực quan (JFrame, JTextField, JTextArea, JButton…) để nhập dữ liệu (From, To, Subject, Body) và hiển thị kết quả giao tiếp với server.

## 📝 4. Hình ảnh chức năng

## 5. Các bước cài đặt
### 1. Bước 1:
### 2. Bước 2:
### 3. Bước 3:
