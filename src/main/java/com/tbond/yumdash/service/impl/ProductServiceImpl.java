package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.domain.Product;
import com.tbond.yumdash.dto.PaginatedResponseDto;
import com.tbond.yumdash.dto.product.ProductRequestDto;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.repository.ProductRepository;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.service.ProductService;
import com.tbond.yumdash.service.exception.ProductNotFoundException;
import com.tbond.yumdash.service.mappers.CategoryMapper;
import com.tbond.yumdash.service.mappers.ProductMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Product createProduct(ProductRequestDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId());

        Product newProduct = Product.builder()
                .title(productDto.getTitle())
                .category(category)
                .productSlug(generateSlug(productDto.getTitle()))
                .productSizes(productDto.getSizes())
                .image(productDto.getImage())
                .discount(productDto.getDiscount())
                .description(productDto.getDescription())
                .build();

        try {
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
    public List<Product> getProductsByCategory(Long categoryId) {
        return productMapper.toProductList(productRepository.findByCategory(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponseDto<ProductResponseDto> getProducts(Integer page) {
        int limit = 10;
        Pageable pageable = PageRequest.of(page, limit);
        Page<ProductEntity> pageProducts = productRepository.findAll(pageable);

        return PaginatedResponseDto.<ProductResponseDto>builder()
                .data(productMapper.toProductResponseDtoList(productMapper.toProductList(pageProducts.getContent())))
                .totalElements(pageProducts.getTotalElements())
                .totalPages(pageProducts.getTotalPages())
                .currentPage(page)
                .build();
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, ProductRequestDto productDto) {
        ProductEntity product = productRepository.findByNaturalId(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        Category category = categoryRepository.findById(productDto.getCategoryId());

        System.out.println(category.getId());

        product.setId(product.getId());
        product.setTitle(productDto.getTitle());
        product.setCategory(categoryMapper.toCategoryEntity(category));
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setRating(product.getRating());
        product.setProductSizes(productDto.getSizes());
        product.setDiscount(productDto.getDiscount());

        try {
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
