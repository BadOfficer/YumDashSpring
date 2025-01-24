package com.tbond.yumdash.event.listener;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.event.EmailVerificationEvent;
import com.tbond.yumdash.service.EmailService;
import com.tbond.yumdash.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailVerificationEventListener implements ApplicationListener<EmailVerificationEvent> {
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(EmailVerificationEvent event) {
        User user = event.getUser();

        String verificationToken = UUID.randomUUID().toString();

        verificationTokenService.saveVerificationToken(user.getId(), verificationToken);

        String url = event.getApplicationUrl() + "/api/v1/auth/verify-email?token=" + verificationToken;

        emailService.sendVerificationEmail(user.getEmail(), url, user.getFirstName());
    }
}
