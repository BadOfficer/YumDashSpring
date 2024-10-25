package com.tbond.yumdash.items.controller;

import com.tbond.yumdash.items.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public String findAllItems(Model model) {
        model.addAttribute("items", itemService.findAllItems());
        return "items";
    }

    @GetMapping
    public String findItemByName(Model model, String title) {
        model.addAttribute("items", itemService.findItemsByName(title));
        return "items";
    }
}
