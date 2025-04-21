package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.Order;
import com.tbond.yumdash.dto.order.OrderRequestDto;

import java.util.UUID;

public interface OrderService {
    Order placeOrder(String userEmail, OrderRequestDto dto);

    String orderPaymentSuccess(UUID orderId);

    String orderPaymentFail(UUID orderId);
}
