package com.tbond.yumdash.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    Long id;
    String title;
}
