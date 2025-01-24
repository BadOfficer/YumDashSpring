package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.ResetPasswordCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordCodeRepository extends JpaRepository<ResetPasswordCodeEntity, Long> {
    Optional<ResetPasswordCodeEntity> findByUserEmailAndVerificationCode(String userEmail, String verificationCode);
}
