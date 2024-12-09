package com.tbond.yumdash.common;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProductSize {
    String size;
    Double price;
}
