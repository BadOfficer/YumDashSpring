package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.CartEntity;
import com.tbond.yumdash.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findCartByUser(UserEntity user);
}
