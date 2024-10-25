package com.tbond.yumdash.items.repository;

import com.tbond.yumdash.items.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepository {
    List<Item> findAllItems();
    List<Item> findItemsByName(String name);
}
