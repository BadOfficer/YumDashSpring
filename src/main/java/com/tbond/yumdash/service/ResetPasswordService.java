package com.tbond.yumdash.service;

public interface ResetPasswordService {
    void saveVerificationCode(String userEmail, String verificationCode);

    String sendResetPasswordCode(String email);

    String resetPassword(String email, String code, String newPassword);
}
