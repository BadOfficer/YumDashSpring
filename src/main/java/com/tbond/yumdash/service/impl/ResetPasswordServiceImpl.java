package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.event.ResetPasswordEvent;
import com.tbond.yumdash.repository.ResetPasswordCodeRepository;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.ResetPasswordCodeEntity;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.ResetPasswordService;
import com.tbond.yumdash.service.exception.ResetPasswordCodeValidationException;
import com.tbond.yumdash.service.exception.UserNotFoundException;
import com.tbond.yumdash.service.mappers.UserMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final ResetPasswordCodeRepository resetPasswordCodeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;

    @Override
    public void saveVerificationCode(String userEmail, String verificationCode) {
        resetPasswordCodeRepository.save(new ResetPasswordCodeEntity(verificationCode, userEmail));
    }

    @Override
    public String sendResetPasswordCode(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        sendResetPasswordCode(userMapper.toUser(user));

        return String.format("Reset code has been sent to %s", email);
    }

    @Override
    @Transactional
    public String resetPassword(String email, String code, String newPassword) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        validateVerificationCode(email, code);

        user.setPassword(passwordEncoder.encode(newPassword));

        try {
            userRepository.save(user);
            return "Password has been successfully reset";
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    private void validateVerificationCode(String userEmail, String verificationCode) {
        ResetPasswordCodeEntity resetPasswordCode = resetPasswordCodeRepository.findByUserEmailAndVerificationCode(userEmail, verificationCode)
                .orElseThrow(() -> new ResetPasswordCodeValidationException("Reset code not found"));

        if (resetPasswordCode.getExpiryTime().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) {
            throw new ResetPasswordCodeValidationException("Expired reset code");
        }

    }

    private void sendResetPasswordCode(User user) {
        eventPublisher.publishEvent(new ResetPasswordEvent(user));
    }
}
