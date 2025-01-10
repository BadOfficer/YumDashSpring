package com.tbond.yumdash.service.mappers;

import com.tbond.yumdash.domain.Cart;
import com.tbond.yumdash.domain.CartItem;
import com.tbond.yumdash.dto.cart.CartItemDto;
import com.tbond.yumdash.dto.cart.CartResponseDto;
import com.tbond.yumdash.repository.entity.CartEntity;
import com.tbond.yumdash.repository.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CartMapper {
    @Mapping(target = "product.id", source = "product.reference")
    CartItem toCartItem(CartItemEntity cartItemEntity);

    @Mapping(target = "totalPrice", source = "totalPrice")
    Cart toCart(CartEntity cartEntity);

    CartItemDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "total", source = "totalPrice")
    CartResponseDto toCartResponseDto(Cart cart);
}
