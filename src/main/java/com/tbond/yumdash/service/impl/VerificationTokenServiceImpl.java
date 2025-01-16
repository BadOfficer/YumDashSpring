package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.repository.VerificationTokenRepository;
import com.tbond.yumdash.repository.entity.VerificationTokenEntity;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.VerificationTokenService;
import com.tbond.yumdash.service.exception.VerificationTokenValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserService userService;

    @Override
    public void saveVerificationToken(String userId, String token) {
        verificationTokenRepository.save(new VerificationTokenEntity(token, userId));
    }

    @Override
    public VerificationTokenEntity validateVerificationToken(String token) {
        VerificationTokenEntity verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new VerificationTokenValidationException("Invalid verification token!");
        }

        if (verificationToken.getExpiryTime().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) {
            verificationTokenRepository.delete(verificationToken);
            userService.deleteUser(UUID.fromString(verificationToken.getUserId()));
            throw new VerificationTokenValidationException("Expired verification token!");
        }

        if (verificationToken.getConfirmedAt() != null) {
            throw new VerificationTokenValidationException("User email is already verified!");
        }

        return verificationToken;
    }
}
