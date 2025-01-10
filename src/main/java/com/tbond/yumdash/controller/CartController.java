package com.tbond.yumdash.controller;

import com.tbond.yumdash.dto.cart.CartResponseDto;
import com.tbond.yumdash.service.CartService;
import com.tbond.yumdash.service.mappers.CartMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @PutMapping("/add-to-cart/{userId}")
    public ResponseEntity<CartResponseDto> addItemToCart(@PathVariable UUID userId,
                                                         @RequestParam UUID productId,
                                                         @RequestParam int quantity,
                                                         @RequestParam String productSize) {
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.addItemToCart(userId, productId, quantity, productSize)));
    }

    @PutMapping("/clear-cart/{userId}")
    public ResponseEntity<CartResponseDto> clearCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.clearCart(userId)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCartByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.getCartByUserId(userId)));
    }

    @PutMapping("/remove-from-cart/{userId}")
    public ResponseEntity<CartResponseDto> removeItemFromCart(@PathVariable UUID userId,
                                                              @RequestParam long cartItemId) {
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.removeItemFromCart(userId, cartItemId)));
    }

    @PutMapping("/update-quantity/{userId}")
    public ResponseEntity<CartResponseDto> updateCartItemQuantity(@PathVariable UUID userId,
                                                                  @RequestParam long cartItemId,
                                                                  @RequestParam int quantity) {
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.updateCartItemQuantity(userId, cartItemId, quantity)));
    }
}
