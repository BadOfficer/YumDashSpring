package com.tbond.yumdash.domain;

import com.tbond.yumdash.repository.entity.CartItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class Cart {
    long id;
    double totalPrice;
    List<CartItemEntity> items;
}
