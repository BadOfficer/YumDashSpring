package com.tbond.yumdash.service;

import com.tbond.yumdash.repository.entity.VerificationTokenEntity;

public interface VerificationTokenService {
    void saveVerificationToken(String userId, String token);

    VerificationTokenEntity validateVerificationToken(String token);
}
