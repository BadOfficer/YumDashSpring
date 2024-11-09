package com.tbond.yumdash.items;

import lombok.Data;

@Data
public class Item {
    private final String title;
    private final String description;
    private final int price;
    private final String image;
    private final int quantity;
}
