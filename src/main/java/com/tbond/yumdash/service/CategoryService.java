package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryRequestDto;

import java.util.List;


public interface CategoryService {
    Category createCategory(CategoryRequestDto categoryDto);

    Category updateCategory(Long id, CategoryRequestDto categoryDto);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String categoryName);

    void deleteCategory(Long id);
}
