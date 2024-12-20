package com.tbond.yumdash.repository.impl;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.repository.CategoryMapper;
import com.tbond.yumdash.repository.CategoryRepository;
import com.tbond.yumdash.repository.entity.CategoryEntity;
import com.tbond.yumdash.service.exception.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM categories", new CategoryMapper());
    }

    public Category findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM categories WHERE id=?", new Object[]{id}, new CategoryMapper())
                .stream()
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException(String.valueOf(id)));
    }

    public Category save(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO categories (title) VALUES (?)",
                    new String[]{"id"});

            ps.setString(1, category.getTitle());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        assert key != null;

        return Category.builder()
                .id(key.longValue())
                .title(category.getTitle()).build();
    }

    public Category update(Long id, Category category) {
        jdbcTemplate.update("UPDATE categories SET title=? WHERE id=?", category.getTitle(), id);

        return Category.builder()
                .id(id)
                .title(category.getTitle())
                .build();
    }

    @Override
    public Category findByName(String name) {
        return  jdbcTemplate.query("SELECT * FROM categories WHERE LOWER(name)=?", new Object[]{name.toLowerCase()}, new CategoryMapper())
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void delete(Long id) {
       jdbcTemplate.update("DELETE FROM categories WHERE id=?", id);
    }
}
