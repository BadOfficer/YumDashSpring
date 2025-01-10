package com.tbond.yumdash.domain;

import lombok.Data;

@Data
public class CartItem {
    long id;
    Product product;
    String productSize;
    double price;
    int quantity;
}
