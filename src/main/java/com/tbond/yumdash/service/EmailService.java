package com.tbond.yumdash.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String url, String firstName) {
        try {
            String subject = "YumDash Verification Email";
            String senderName = "YumDash";
            Path templatePath = new ClassPathResource("/templates/email/verification.html").getFile().toPath();
            String emailContent = Files.readString(templatePath);

            emailContent = emailContent.replace("{{url}}", url);
            emailContent = emailContent.replace("{{firstName}}", firstName);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(emailContent, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("yumdash.help@gmail.com", senderName);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
