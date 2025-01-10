package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.common.ProductSize;
import com.tbond.yumdash.domain.Cart;
import com.tbond.yumdash.repository.CartRepository;
import com.tbond.yumdash.repository.ProductRepository;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.CartEntity;
import com.tbond.yumdash.repository.entity.CartItemEntity;
import com.tbond.yumdash.repository.entity.ProductEntity;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.CartService;
import com.tbond.yumdash.service.exception.CartItemNotFound;
import com.tbond.yumdash.service.exception.ProductNotFoundException;
import com.tbond.yumdash.service.exception.UserNotFoundException;
import com.tbond.yumdash.service.mappers.CartMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public Cart addItemToCart(UUID userId, UUID productId, Integer quantity, String productSize) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        ProductEntity product = productRepository.findByNaturalId(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));
        CartEntity cart = user.getCart();

        boolean existingItem = cart.getItems().stream()
                .anyMatch(item ->
                        item.getProduct().getReference().equals(product.getReference()) && item.getProductSize().equals(productSize));

        if (!existingItem) {
            Double price = product.getProductSizes().stream().filter(size -> size.getSize().equals(productSize))
                    .map(ProductSize::getPrice).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product size: " + productSize));

            Double calculatedPrice = getCalculatedPrice(price, quantity, product.getDiscount());

            CartItemEntity cartItem = CartItemEntity.builder()
                    .cart(cart)
                    .price(calculatedPrice)
                    .productSize(productSize)
                    .quantity(quantity)
                    .product(product)
                    .build();

            cartItem.getCart().getItems().add(cartItem);
            cart.setTotalPrice(cart.getTotalPrice() + calculatedPrice);

            try {
                return cartMapper.toCart(cartRepository.save(cart));
            } catch (Exception e) {
                throw new PersistenceException(e);
            }
        } else {
            throw new RuntimeException("Item already in cart");
        }
    }

    @Override
    @Transactional
    public Cart clearCart(UUID userId) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        CartEntity cart = user.getCart();
        cart.setTotalPrice(0.0);
        cart.getItems().clear();

        try {
            return cartMapper.toCart(cartRepository.save(cart));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCartByUserId(UUID userId) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        CartEntity cart = user.getCart();

        return cartMapper.toCart(cart);
    }

    @Override
    @Transactional
    public Cart removeItemFromCart(UUID userId, Long cartItemId) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        CartEntity cart = user.getCart();

        CartItemEntity cartItem = cart.getItems().stream().filter(item -> item.getId().equals(cartItemId))
                .findFirst().orElseThrow(() -> new CartItemNotFound(cartItemId.toString()));

        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());

        cart.getItems().remove(cartItem);

        try {
            return cartMapper.toCart(cartRepository.save(cart));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Cart updateCartItemQuantity(UUID userId, Long cartItemId, Integer quantity) {
        UserEntity user = userRepository.findByNaturalId(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        CartEntity cart = user.getCart();

        CartItemEntity cartItem = cart.getItems().stream().filter(item -> item.getId().equals(cartItemId))
                .findFirst().orElseThrow(() -> new CartItemNotFound(cartItemId.toString()));

        ProductEntity product = productRepository.findByNaturalId(cartItem.getProduct().getReference())
                .orElseThrow(() -> new ProductNotFoundException(cartItem.getProduct().getReference().toString()));

        Double price = product.getProductSizes().stream().filter(size -> size.getSize().equals(cartItem.getProductSize()))
                .map(ProductSize::getPrice).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid product size: " + cartItem.getProductSize()));

        Double calculatedPrice = getCalculatedPrice(price, quantity, product.getDiscount());

        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());

        cartItem.setQuantity(quantity);
        cartItem.setPrice(calculatedPrice);

        cart.setTotalPrice(cart.getTotalPrice() + calculatedPrice);

        try {
            return cartMapper.toCart(cartRepository.save(cart));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    private Double getCalculatedPrice(Double price, Integer quantity, Double discount) {
        Double roundedPrice = (double) Math.round((price - (price * discount / 100)) * 100) / 100;

        return roundedPrice * quantity;
    }
}
