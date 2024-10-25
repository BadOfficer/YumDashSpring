package com.tbond.yumdash.items.service.impl;

import com.tbond.yumdash.items.Item;
import com.tbond.yumdash.items.repository.impl.ItemRepositoryImpl;
import com.tbond.yumdash.items.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdItemService implements ItemService {
    private ItemRepositoryImpl itemRepository;

    @Autowired
    public void setItemRepository(ItemRepositoryImpl itemRepository) {
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
