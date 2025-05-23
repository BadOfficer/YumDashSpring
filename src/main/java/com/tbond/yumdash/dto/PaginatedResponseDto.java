package com.tbond.yumdash.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponseDto<T> {
    List<T> data;
    long totalElements;
    int totalPages;
}
