package com.tbond.yumdash.service.mappers;

import com.tbond.yumdash.domain.Order;
import com.tbond.yumdash.dto.order.OrderResponseDto;
import com.tbond.yumdash.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "reference", target = "id")
    @Mapping(source = "cartItems", target = "cartItems")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "courier", target = "courier")
    @Mapping(source = "status", target = "orderStatus")
    @Mapping(source = "customer.reference", target = "customer.id")
    @Mapping(source = "courier.reference", target = "courier.id")
    Order toOrder(OrderEntity orderEntity);

    OrderResponseDto toOrderResponseDto(Order order);
}
