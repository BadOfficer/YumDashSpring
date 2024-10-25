package com.tbond.yumdash.items.repository.impl;

import com.tbond.yumdash.items.Item;
import com.tbond.yumdash.items.repository.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final List<Item> items = Arrays.asList(
            new Item("Cheeseburger", "Juicy beef patty, melted cheddar cheese, fresh vegetables, and sauce on a soft bun.", 45, ""),
            new Item("French Fries", "Golden, crispy fries, perfectly seasoned with sea salt.", 2, ""),
            new Item("Chicken Nuggets", "Crispy breaded nuggets made with tender chicken fillet.", 5, "")
    );

    public List<Item> findAllItems() {
        return items;
    }

    public List<Item> findItemsByName(String name) {
        return items.stream().filter(item -> item.getTitle().equals(name)).toList();
    }
}
