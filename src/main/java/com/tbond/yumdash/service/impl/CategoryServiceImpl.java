package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.dto.category.CategoryRequestDto;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.CategoryService;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.mappers.CategoryMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductService productService;


    @Override
    @Transactional
    public Category createCategory(CategoryRequestDto categoryDto) {
        try {
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
        Page<ProductEntity> products = productService.getProductsByCategory(id, 10, 0);

        if (products.getTotalElements() == 0) {
            try {
                categoryRepository.delete(id);
            } catch (Exception e) {
                throw new PersistenceException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Cannot delete category with id " + id + " because it is associated with "
                    + products.getTotalElements() + " products.");
        }
    }
}

