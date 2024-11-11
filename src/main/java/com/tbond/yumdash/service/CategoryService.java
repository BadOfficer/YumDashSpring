package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryDto;

import java.util.List;


public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    Category updateCategory(CategoryDto categoryDto, String categoryId);

    List<Category> getAllCategories(String name, int page, int size);

    Category getCategoryById(String categoryId);

    String deleteCategory(String categoryId);
}
