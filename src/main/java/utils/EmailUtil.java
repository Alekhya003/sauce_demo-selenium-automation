package utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

public class EmailUtil {

    private static final String EMAIL_USERNAME = System.getenv("EMAIL_USERNAME");
    private static final String EMAIL_PASSWORD = System.getenv("EMAIL_PASSWORD");

    private JavaMailSender mailSender;

    public EmailUtil() {

        if (EMAIL_USERNAME == null || EMAIL_PASSWORD == null) {
            System.err.println("Email credentials not set. Skipping email notification.");
            return;
        }

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername(EMAIL_USERNAME);
        sender.setPassword(EMAIL_PASSWORD);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        this.mailSender = sender;
    }

    public void mailSend(String report, String to , String suiteName) {

        if (mailSender == null) {
            System.err.println("MailSender not initialized. Email skipped.");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            DateAndTime d = new DateAndTime();
            helper.setTo(to);
            helper.setSubject("Test Automation Report : " + "-" + suiteName + " " +d.dateTime());
            helper.setText(report);

            mailSender.send(message);
            System.out.println("Email sent successfully to " + to);

        } catch (MessagingException e) {
            System.err.println("Email send failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
