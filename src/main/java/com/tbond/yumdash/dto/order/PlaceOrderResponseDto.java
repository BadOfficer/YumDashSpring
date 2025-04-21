package com.tbond.yumdash.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PlaceOrderResponseDto {
    private String message;
    private String orderId;
    private String paymentUrl;
}
