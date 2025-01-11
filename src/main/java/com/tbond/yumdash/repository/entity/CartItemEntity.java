package com.tbond.yumdash.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity product;

    String productSize;

    Double price;

    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    OrderEntity order;
}
