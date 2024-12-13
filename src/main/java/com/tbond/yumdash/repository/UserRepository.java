package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends NaturalIdRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
}
