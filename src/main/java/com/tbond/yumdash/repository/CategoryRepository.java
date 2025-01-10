package com.tbond.yumdash.repository;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
