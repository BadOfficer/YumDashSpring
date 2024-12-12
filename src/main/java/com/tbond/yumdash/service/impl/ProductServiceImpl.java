package com.tbond.yumdash.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbond.yumdash.common.ProductSize;
import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.domain.Product;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.repository.ProductRepository;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.exception.ProductNotFoundException;
import com.tbond.yumdash.service.mappers.CategoryMapper;
import com.tbond.yumdash.service.mappers.ProductMapper;
import com.tbond.yumdash.utils.ImagesUtils;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.tbond.yumdash.utils.SlugUtils.generateSlug;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ObjectMapper objectMapper;
    private final ImagesUtils imagesUtils;

    @Override
    @Transactional
    public Product createProduct(ProductRequestDto productDto) {
        try {
            Category category = categoryRepository.findById(productDto.getCategoryId());

            List<ProductSize> sizes = objectMapper.readValue(productDto.getSizes(), new TypeReference<>() {});
            String imagePath = imagesUtils.saveImage(productDto.getImage());

            Product newProduct = Product.builder()
                    .title(productDto.getTitle())
                    .category(category)
                    .productSlug(generateSlug(productDto.getTitle()))
                    .productSizes(sizes)
                    .image(imagePath)
                    .discount(productDto.getDiscount())
                    .description(productDto.getDescription())
                    .build();

            return productMapper.toProduct(productRepository.save(productMapper.toProductEntity(newProduct)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public Product getProductById(UUID productId) {
        return productMapper.toProduct(productRepository.findByNaturalId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString())));
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductBySlug(String slug) {
        return productMapper.toProduct(productRepository.findBySlug(slug));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntity> getProductsByCategory(Long categoryId, Integer limit, Integer offset) {
        return productRepository.findByCategory(categoryId, PageRequest.of(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntity> getProducts(Integer limit, Integer offset) {
        return productRepository.findAll(PageRequest.of(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntity> getProductsByTitle(String title, Integer offset, Integer limit) {
        return productRepository.findByTitleContainingIgnoreCase(title, PageRequest.of(offset, limit));
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, ProductRequestDto productDto) {
        try {
            ProductEntity product = productRepository.findByNaturalId(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId.toString()));

            List<ProductSize> sizes = objectMapper.readValue(productDto.getSizes(), new TypeReference<>() {});
            Category category = categoryRepository.findById(productDto.getCategoryId());
            String imagePath = imagesUtils.saveImage(productDto.getImage());

            if (imagePath != null) {
                product.setImage(imagePath);
            }

            product.setId(product.getId());
            product.setTitle(productDto.getTitle());
            product.setCategory(categoryMapper.toCategoryEntity(category));
            product.setDescription(productDto.getDescription());
            product.setRating(product.getRating());
            product.setProductSizes(sizes);
            product.setDiscount(productDto.getDiscount());

            return productMapper.toProduct(productRepository.save(product));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        getProductById(productId);

        try {
            productRepository.deleteByNaturalId(productId);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
