package uz.smart_ai.smart_ai.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Autowired
    private JavaMailSender mailSender;

    private void sendMimeEmail(String email, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setFrom(fromAccount);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRegistrationEmail(String phoneNumber, Long id) {
        String subject = "Complete Registration";
        String body =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Account Confirmation</title>\n" +
                        "    <style>\n" +
                        "        a {\n" +
                        "            padding: 10px 30px;\n" +
                        "            display: inline-block;\n" +
                        "            background-color: #4CAF50;\n" +
                        "            color: #ffffff;\n" +
                        "            text-decoration: none;\n" +
                        "            border-radius: 5px;\n" +
                        "            font-weight: bold;\n" +
                        "        }\n" +
                        "        .button-link:hover {\n" +
                        "            background-color: #45a049;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            line-height: 1.6;\n" +
                        "            text-align: center;\n" +
                        "            padding: 20px;\n" +
                        "        }\n" +
                        "        .footer {\n" +
                        "            margin-top: 20px;\n" +
                        "            color: #777;\n" +
                        "            font-size: 12px;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"container\">\n" +
                        "        <h1>Hisobingizni Tasdiqlang</h1>\n" +
                        "        <p>Hurmatli foydalanuvchi,</p>\n" +
                        "        <p>Hisobingizni tasdiqlash uchun quyidagi tugmani bosing:</p>\n" +
                        "        <a href=\"http://localhost:8080/auth/registration/verification/?id=" + id + "\">Tasdiqlash</a>\n" +
                        "        <p>Agar ushbu so‘rovni siz yubormagan bo‘lsangiz, bu xabarni e'tiborsiz qoldiring.</p>\n" +
                        "        <div class=\"footer\">\n" +
                        "            <p>© 2025 Sizning Loyihangiz. Barcha huquqlar himoyalangan.</p>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        sendMimeEmail(phoneNumber, subject, body);
    }
}
