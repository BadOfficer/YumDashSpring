package com.tbond.yumdash.common;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    Double latitude;
    Double longitude;
}
