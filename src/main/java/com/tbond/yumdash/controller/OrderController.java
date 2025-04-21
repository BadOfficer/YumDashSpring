package com.tbond.yumdash.controller;

import com.tbond.yumdash.common.PaymentMethod;
import com.tbond.yumdash.domain.Order;
import com.tbond.yumdash.dto.order.OrderRequestDto;
import com.tbond.yumdash.dto.order.PlaceOrderResponseDto;
import com.tbond.yumdash.service.OrderService;
import com.tbond.yumdash.service.PaymentService;
import com.tbond.yumdash.service.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    @PostMapping("/place-new-order")
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(Authentication authentication,
                                                            @RequestBody OrderRequestDto dto) {
        Order newOrder = orderService.placeOrder(authentication.getName(), dto);

        PlaceOrderResponseDto orderPlaceResponse = PlaceOrderResponseDto.builder()
                .orderId(newOrder.getId())
                .message(String.format("Order %s placed successfully", newOrder.getId()))
                .build();

        if (newOrder.getPaymentMethod().equals(PaymentMethod.ONLINE_PAYMENT)) {
            orderPlaceResponse.setPaymentUrl(paymentService.createPaymentLink(newOrder));
        }

        return ResponseEntity.ok(orderPlaceResponse);
    }

    @GetMapping("/success/{orderId}")
    public ResponseEntity<String> orderPaymentStatusToSuccess(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.orderPaymentSuccess(orderId));
    }

    @GetMapping("/failed/{orderId}")
    public ResponseEntity<String> orderPaymentStatusToFailed(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.orderPaymentFail(orderId));
    }
}
