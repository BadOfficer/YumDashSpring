package com.tbond.yumdash.repository.entity;

import com.tbond.yumdash.common.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    OrderStatus status;

    @Column(nullable = false, name = "total_price")
    Double totalPrice;

    @OneToMany(mappedBy = "order")
    List<CartItemEntity> cartItems;

    @NaturalId
    @Column(nullable = false, name = "order_reference", unique = true)
    UUID reference;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Column(nullable = false, name = "created_at")
    Long createdAt;
}
