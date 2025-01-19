package com.tbond.yumdash.controller;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.cart.CartResponseDto;
import com.tbond.yumdash.service.CartService;
import com.tbond.yumdash.service.UserService;
import com.tbond.yumdash.service.mappers.CartMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;
    private final UserService userService;

    @PutMapping("/add-to-cart")
    public ResponseEntity<CartResponseDto> addItemToCart(@RequestParam UUID productId,
                                                         @RequestParam int quantity,
                                                         @RequestParam String productSize,
                                                         Authentication authentication) {
        UUID userId = getUserId(authentication);

        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.addItemToCart(userId, productId, quantity, productSize)));
    }

    @PutMapping("/clear-cart")
    public ResponseEntity<CartResponseDto> clearCart(Authentication authentication) {
        UUID userId = getUserId(authentication);

        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.clearCart(userId)));
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCartByUserId(Authentication authentication) {
        UUID userId = getUserId(authentication);

        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.getCartByUserId(userId)));
    }

    @PutMapping("/remove-from-cart")
    public ResponseEntity<CartResponseDto> removeItemFromCart(@RequestParam long cartItemId,
                                                              Authentication authentication) {
        UUID userId = getUserId(authentication);

        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.removeItemFromCart(userId, cartItemId)));
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<CartResponseDto> updateCartItemQuantity(@RequestParam long cartItemId,
                                                                  @RequestParam int quantity,
                                                                  Authentication authentication) {
        UUID userId = getUserId(authentication);

        return ResponseEntity.ok(cartMapper.toCartResponseDto(cartService.updateCartItemQuantity(userId, cartItemId, quantity)));
    }

    private UUID getUserId(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());

        return UUID.fromString(user.getId());
    }
}
