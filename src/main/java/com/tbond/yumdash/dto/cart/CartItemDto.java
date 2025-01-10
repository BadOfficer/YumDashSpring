package com.tbond.yumdash.dto.cart;

import com.tbond.yumdash.dto.product.ProductCartItemDto;
import lombok.Data;

@Data
public class CartItemDto {
    long id;
    ProductCartItemDto product;
    String productSize;
    double price;
    int quantity;
}
