package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.CartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<CartEntity, Long> {
}
