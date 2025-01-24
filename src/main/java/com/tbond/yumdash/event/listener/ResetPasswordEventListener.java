package com.tbond.yumdash.event.listener;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.event.ResetPasswordEvent;
import com.tbond.yumdash.service.EmailService;
import com.tbond.yumdash.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class ResetPasswordEventListener implements ApplicationListener<ResetPasswordEvent> {
    private final ResetPasswordService resetPasswordService;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(ResetPasswordEvent event) {
        User user = event.getUser();

        String verificationCode = generateCode();

        resetPasswordService.saveVerificationCode(user.getEmail(), verificationCode);

        emailService.sendResetPasswordCode(user.getEmail(), user.getFirstName(), verificationCode);
    }

    public static String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
