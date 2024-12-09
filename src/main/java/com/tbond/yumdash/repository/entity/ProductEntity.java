package com.tbond.yumdash.repository.entity;

import com.tbond.yumdash.common.ProductSize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"title", "category_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    @Column
    Double rating;

    @Column
    Double discount;

    @Column
    String image;

    @Column(nullable = false, name = "product_slug", unique = true)
    String slug;

    @NaturalId
    @Column(nullable = false, name = "product_reference", unique = true)
    UUID reference;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = ProductSize.class)
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    List<ProductSize> productSizes;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    CategoryEntity category;
}
