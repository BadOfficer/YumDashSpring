package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserCreateDto;
import com.tbond.yumdash.event.EmailVerificationEvent;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.VerificationTokenRepository;
import com.tbond.yumdash.repository.entity.VerificationTokenEntity;
import com.tbond.yumdash.service.AuthService;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public User register(UserCreateDto dto, HttpServletRequest request) {
        User user = userService.createUser(dto);

        sendVerificationCode(user, request);

        return user;
    }

    @Override
    public String verifyRegistration(String token) {
        try {
            VerificationTokenEntity verificationToken = verificationTokenService.validateVerificationToken(token);

            userRepository.enableUser(UUID.fromString(verificationToken.getUserId()));
            verificationTokenRepository.updateConfirmedAt(verificationToken.getToken(), LocalDateTime.now());

            return "Email is verified successfully!";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private void sendVerificationCode(User user, HttpServletRequest request) {
        eventPublisher.publishEvent(new EmailVerificationEvent(user, applicationServerUrl(request)));
    }

    private String applicationServerUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
