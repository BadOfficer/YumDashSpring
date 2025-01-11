package com.tbond.yumdash.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbond.yumdash.common.ProductSize;
import com.tbond.yumdash.domain.Product;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.repository.ProductRepository;
import com.tbond.yumdash.repository.entity.CategoryEntity;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.exception.CategoryNotFoundException;
import com.tbond.yumdash.service.exception.ProductNotFoundException;
import com.tbond.yumdash.service.mappers.CategoryMapper;
import com.tbond.yumdash.service.mappers.ProductMapper;
import com.tbond.yumdash.utils.FileUploadUtils;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tbond.yumdash.utils.SlugUtils.generateSlug;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ObjectMapper objectMapper;
    private final FileUploadUtils fileUploadUtils;

    @Override
    @Transactional
    public Product createProduct(ProductRequestDto productDto) {
        try {
            CategoryEntity category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId().toString()));

            List<ProductSize> sizes = objectMapper.readValue(productDto.getSizes(), new TypeReference<>() {
            });
            String imagePath = fileUploadUtils.saveImage(productDto.getImage());

            Product newProduct = Product.builder()
                    .title(productDto.getTitle())
                    .category(categoryMapper.toCategory(category))
                    .productSlug(generateSlug(productDto.getTitle(), category.getTitle()))
                    .productSizes(sizes)
                    .image(imagePath)
                    .discount(Optional.ofNullable(productDto.getDiscount()).orElse(0.0))
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
        return productRepository.findByCategoryId(categoryId, PageRequest.of(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntity> getProducts(Integer limit, Integer offset) {
        return productRepository.findAll(PageRequest.of(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByTitle(String title) {
        return productMapper.toProductList(productRepository.findByTitleContainingIgnoreCase(title).stream()
                .limit(10)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, ProductRequestDto productDto) {
        try {
            ProductEntity product = productRepository.findByNaturalId(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId.toString()));

            List<ProductSize> sizes = objectMapper.readValue(productDto.getSizes(), new TypeReference<>() {
            });
            CategoryEntity category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId().toString()));

            String imagePath = fileUploadUtils.saveImage(productDto.getImage());

            product.setId(product.getId());
            product.setTitle(productDto.getTitle());
            product.setCategory(category);
            product.setDescription(productDto.getDescription());
            product.setRating(product.getRating());
            product.setProductSizes(sizes);
            product.setImage(Optional.ofNullable(imagePath).orElse(product.getImage()));
            product.setDiscount(Optional.ofNullable(productDto.getDiscount()).orElse(product.getDiscount()));

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
