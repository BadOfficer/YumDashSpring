package com.tbond.yumdash.repository.entity;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.OrderStatus;
import com.tbond.yumdash.common.PaymentStatus;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItemEntity> cartItems;

    @Column(nullable = false, name = "total_price")
    Double totalPrice;

    @Column(nullable = false, name = "delivery_address")
    Address deliveryAddress;

    @Column(nullable = false)
    OrderStatus status;

    @Column(name = "payment_status", nullable = false)
    PaymentStatus paymentStatus;

    @NaturalId
    @Column(nullable = false, name = "order_reference", unique = true)
    UUID reference;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @OneToOne
    @JoinColumn(name = "courier_id")
    UserEntity courier;

    @Column(nullable = false, name = "created_at")
    Long createdAt;

    @Column(name = "completed_at")
    Long completedAt;
}
