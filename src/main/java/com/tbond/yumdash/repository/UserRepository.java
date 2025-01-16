package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends NaturalIdRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.isEnabled = true WHERE u.reference = :reference")
    void enableUser(UUID reference);
}
