package com.tbond.yumdash.dto.order;

import com.tbond.yumdash.common.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank(message = "Payment method cannot be empty")
    PaymentMethod paymentMethod;

    @NotBlank(message = "City cannot be empty")
    String city;

    @NotBlank(message = "Street cannot be empty")
    String street;

    @NotBlank(message = "House number cannot be empty")
    String houseNumber;
}
