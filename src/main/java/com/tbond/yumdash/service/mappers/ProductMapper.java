package com.tbond.yumdash.service.mappers;

import com.tbond.yumdash.domain.Product;
import com.tbond.yumdash.dto.product.ProductResponseDto;
import com.tbond.yumdash.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

    @Mapping(target = "id", source = "reference")
    @Mapping(target = "productSlug", source = "slug")
    Product toProduct(ProductEntity productEntity);

    @Mapping(target = "reference", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "slug", source = "productSlug")
    ProductEntity toProductEntity(Product product);

    @Mapping(target = "sizes", source = "productSizes")
    @Mapping(target = "productSlug", source = "productSlug")
    ProductResponseDto toProductResponseDto(Product product);

    List<Product> toProductList(List<ProductEntity> productEntities);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> products);
}
