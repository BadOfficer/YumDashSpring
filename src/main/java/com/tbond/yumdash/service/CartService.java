package com.tbond.yumdash.service;

import com.tbond.yumdash.domain.Cart;

import java.util.UUID;

public interface CartService {
    Cart addItemToCart(UUID userId, UUID productId, Integer quantity, String size);

    Cart clearCart(UUID userId);

    Cart getCartByUserId(UUID userId);

    Cart removeItemFromCart(UUID userId, Long cartItemId);

    Cart updateCartItemQuantity(UUID userId, Long cartItemId, Integer quantity);
}
