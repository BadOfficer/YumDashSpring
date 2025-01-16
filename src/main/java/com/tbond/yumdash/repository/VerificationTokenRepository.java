package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {
    VerificationTokenEntity findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE VerificationTokenEntity v SET v.confirmedAt = :confirmedAt WHERE v.token = :token")
    void updateConfirmedAt(String token, java.time.LocalDateTime confirmedAt);
}
