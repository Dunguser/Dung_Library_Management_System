
package org.model.operator;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailSender {
    private static final String EMAIL_FROM = "nguyenxuandung457@gmail.com"; // Email của bạn
    private static final String APP_PASSWORD = "kogo heqa jzwj tqnr"; // Mật khẩu ứng dụng Gmail
    private static Message message;

    public static void sendEmail(String emailTo, String content, int type) {
        // Tạo giao diện loading
        LoadingScreen loadingScreen = new LoadingScreen();

        // Chạy luồng riêng để gửi email (tránh treo giao diện chính)
        new Thread(() -> {
            try {
                // Hiển thị hiệu ứng loading
                if (type == 1 || type == 2) loadingScreen.show();

                // Tạo email
                message = new MimeMessage(getEmailSession());
                message.setFrom(new InternetAddress(EMAIL_FROM));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
                if (type == 1) {
                    message.setSubject("Confirmation Code for Your Library Management System Request");
                } else if (type == 2) {
                    message.setSubject(" Thank You for Joining Our Library Management App!");
                } else if (type == 3) {
                    message.setSubject("Welcome to Our Library Management App!");
                }
                message.setText(content);

                // Gửi email
                Transport.send(message);

                // Đóng hiệu ứng loading
                if (type == 1 || type == 2) loadingScreen.close();

                // Hiển thị thông báo thành công
                if (type == 1 || type == 2) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert((Alert.AlertType.INFORMATION));
                        if (type == 1) {
                            alert.setTitle("Email Sent");
                            alert.setHeaderText(null);
                            alert.setContentText("The email has been sent successfully!");
                        } else if (type == 2) {
                            alert.setTitle("Registration Successful");
                            alert.setHeaderText(null);
                            alert.setContentText("You have successfully registered!");
                        }
                        alert.showAndWait();
                    });
                }
            } catch (Exception e) {
                // Đóng hiệu ứng loading khi có lỗi
                loadingScreen.close();

                // Hiển thị thông báo lỗi
                Platform.runLater(() -> {
                    Alert alert = new Alert((Alert.AlertType.ERROR));
                    alert.setTitle("Email Sending Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to send the email: " + e.getMessage());
                    alert.showAndWait();
                });
            }
        }).start();
    }

    private static Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private static Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}

