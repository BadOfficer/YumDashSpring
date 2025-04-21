package com.tbond.yumdash.repository;

import com.tbond.yumdash.repository.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {

}
