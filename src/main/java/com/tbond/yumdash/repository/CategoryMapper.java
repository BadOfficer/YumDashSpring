package com.tbond.yumdash.repository;

import com.tbond.yumdash.domain.Category;
import com.tbond.yumdash.repository.entity.CategoryEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Category.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .build();
    }
}
