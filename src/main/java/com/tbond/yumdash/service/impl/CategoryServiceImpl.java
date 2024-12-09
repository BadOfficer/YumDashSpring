package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryRequestDto;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.service.CategoryService;
import com.tbond.yumdash.service.mappers.CategoryMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    @Transactional
    public Category createCategory(CategoryRequestDto categoryDto) {
        try {
            System.out.println(categoryDto.getTitle());
            return categoryRepository.save(categoryMapper.toCategory(categoryDto));
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryRequestDto categoryDto) {
        try {
            return categoryRepository.update(id, categoryMapper.toCategory(categoryDto));
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        getCategoryById(id);

        try {
            categoryRepository.delete(id);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}
