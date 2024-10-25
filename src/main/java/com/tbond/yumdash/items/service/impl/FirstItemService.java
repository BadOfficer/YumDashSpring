package com.tbond.yumdash.items.service.impl;

import com.tbond.yumdash.items.Item;
import com.tbond.yumdash.items.repository.impl.ItemRepositoryImpl;
import com.tbond.yumdash.items.service.ItemService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class FirstItemService implements ItemService {

    private final ItemRepositoryImpl itemRepository;

    public FirstItemService(ItemRepositoryImpl itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAllItems();
    }

    @Override
    public List<Item> findItemsByName(String name) {
        return itemRepository.findItemsByName(name);
    }
}
