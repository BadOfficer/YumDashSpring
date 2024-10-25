package com.tbond.yumdash.items.service;

import com.tbond.yumdash.items.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAllItems();

    List<Item> findItemsByName(String name);
}
