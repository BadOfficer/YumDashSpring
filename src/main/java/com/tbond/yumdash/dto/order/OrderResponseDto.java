package com.tbond.yumdash.dto.order;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.OrderStatus;
import com.tbond.yumdash.common.PaymentMethod;
import com.tbond.yumdash.common.PaymentStatus;
import com.tbond.yumdash.dto.user.CustomerOrderResponseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {
    private String id;
    private Double totalPrice;
    private Address deliveryAddress;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private CustomerOrderResponseDto customer;
    private CustomerOrderResponseDto courier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private String paymentUrl;
}
