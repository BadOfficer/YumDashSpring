package com.tbond.yumdash.dto.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    long id;
    double total;
    List<CartItemDto> items;
}
