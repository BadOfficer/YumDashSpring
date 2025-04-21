package com.tbond.yumdash.repository.entity;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.OrderStatus;
import com.tbond.yumdash.common.PaymentMethod;
import com.tbond.yumdash.common.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    @Embedded
    Address deliveryAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @NaturalId
    @Column(nullable = false, name = "order_reference", unique = true)
    UUID reference;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    UserEntity customer;

    @OneToOne
    @JoinColumn(name = "courier_id")
    UserEntity courier;

    @Column(nullable = false, name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "completed_at")
    LocalDateTime completedAt;

    @Column(nullable = false, name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
