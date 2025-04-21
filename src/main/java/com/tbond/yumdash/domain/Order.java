package com.tbond.yumdash.domain;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.OrderStatus;
import com.tbond.yumdash.common.PaymentMethod;
import com.tbond.yumdash.common.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String id;
    private List<CartItem> cartItems;
    private double totalPrice;
    private Address deliveryAddress;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private User customer;
    private User courier;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;
}
