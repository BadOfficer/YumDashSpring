package com.tbond.yumdash.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    String city;
    String street;
    String houseNumber;
}
