package com.tbond.yumdash.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Category {
    Long id;
    String name;
    String description;
}
