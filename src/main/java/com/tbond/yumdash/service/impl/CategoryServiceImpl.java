package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryDto;
import com.tbond.yumdash.service.CategoryService;
import com.tbond.yumdash.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {
    List<Category> categories = new ArrayList<>(List.of(
            Category.builder().id(UUID.fromString("b15d7f0c-e5c4-4b47-a41a-48c1b75f3c4f")).name("Category 1").description("Description for category 1").build(),
            Category.builder().id(UUID.fromString("b15d7f0c-e5c4-4b47-a41a-48c1b75f3c4c")).name("Category 2").description("Description for category 2").build()
    ));

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category newCategory = Category.builder()
                .id(UUID.randomUUID())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();

        categories.add(newCategory);

        return newCategory;
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = getCategoryById(categoryId);

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return category;
    }

    @Override
    public List<Category> getAllCategories(String name, int page, int size) {
        Stream<Category> filteredCategories = categories.stream()
                .filter(category -> name == null || name.isEmpty() ||
                        category.getName().toLowerCase().contains(name.toLowerCase()));

        return filteredCategories
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @Override
    public Category getCategoryById(String categoryId) {
        return categories.stream().filter(category -> category.getId().toString().equals(categoryId)).findFirst().orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Override
    public String deleteCategory(String categoryId) {
        Category category = getCategoryById(categoryId);
        categories.remove(category);

        return "Category " + category.getName() + " deleted";
    }
}
