package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryRequestDto;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.repository.ProductRepository;
import com.tbond.yumdash.repository.entity.CategoryEntity;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.CategoryService;
import com.tbond.yumdash.service.exception.CategoryNotFoundException;
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
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public Category createCategory(CategoryRequestDto categoryDto) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .title(categoryDto.getTitle())
                .build();

        try {
            return categoryMapper.toCategory(categoryRepository.save(categoryEntity));
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryRequestDto categoryDto) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id.toString()));

        category.setTitle(categoryDto.getTitle());

        try {
            return categoryMapper.toCategory(categoryRepository.save(category));
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id.toString()));

        return categoryMapper.toCategory(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id.toString()));
        List<ProductEntity> products = productRepository.findByCategoryId(category.getId());

        try {
            for (ProductEntity productEntity : products) {
                productEntity.setCategory(null);
                productRepository.save(productEntity);
            }

            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}

