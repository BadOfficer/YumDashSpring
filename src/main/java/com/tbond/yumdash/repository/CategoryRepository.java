package com.tbond.yumdash.repository;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.repository.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    Category findByName(String name);
    void delete(Long id);
}
