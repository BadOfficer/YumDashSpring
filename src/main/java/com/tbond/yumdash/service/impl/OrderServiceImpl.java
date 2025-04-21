package com.tbond.yumdash.service.impl;

import com.tbond.yumdash.common.Address;
import com.tbond.yumdash.common.OrderStatus;
import com.tbond.yumdash.common.PaymentMethod;
import com.tbond.yumdash.common.PaymentStatus;
import com.tbond.yumdash.domain.Order;
import com.tbond.yumdash.dto.order.OrderRequestDto;
import com.tbond.yumdash.repository.CartItemRepository;
import com.tbond.yumdash.repository.CartRepository;
import com.tbond.yumdash.repository.OrderRepository;
import com.tbond.yumdash.repository.UserRepository;
import com.tbond.yumdash.repository.entity.CartEntity;
import com.tbond.yumdash.repository.entity.CartItemEntity;
import com.tbond.yumdash.repository.entity.OrderEntity;
import com.tbond.yumdash.repository.entity.UserEntity;
import com.tbond.yumdash.service.OrderService;
import com.tbond.yumdash.service.exception.OrderNotFoundException;
import com.tbond.yumdash.service.exception.UserNotFoundException;
import com.tbond.yumdash.service.exception.order.PlaceOrderException;
import com.tbond.yumdash.service.mappers.OrderMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public Order placeOrder(String userEmail, OrderRequestDto dto) {
        UserEntity customer = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));

        Address deliveryAddress = Address.builder()
                .city(dto.getCity())
                .street(dto.getStreet())
                .houseNumber(dto.getHouseNumber())
                .build();

        CartEntity customerCart = customer.getCart();
        List<CartItemEntity> cartItems = new ArrayList<>(customerCart.getItems());
        double orderTotalPrice = customerCart.getTotalPrice();

        if (cartItems.isEmpty() || orderTotalPrice <= 0) {
            throw new PlaceOrderException("Cart is empty");
        }

        PaymentStatus paymentStatus = dto.getPaymentMethod() == PaymentMethod.ONLINE_PAYMENT
                ? PaymentStatus.PROCESSING
                : PaymentStatus.UPON_DELIVERY;
        OrderEntity newOrder = OrderEntity.builder()
                .cartItems(cartItems)
                .totalPrice(orderTotalPrice)
                .status(OrderStatus.PROCESSING)
                .paymentStatus(paymentStatus)
                .paymentMethod(dto.getPaymentMethod())
                .deliveryAddress(deliveryAddress)
                .reference(UUID.randomUUID())
                .customer(customer)
                .build();

        try {
            customerCart.setTotalPrice(0.0);
            cartRepository.save(customerCart);

            for (CartItemEntity cartItem : cartItems) {
                cartItem.setOrder(newOrder);
                cartItem.setCart(null);
                cartItemRepository.save(cartItem);
            }

            return orderMapper.toOrder(orderRepository.save(newOrder));
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public String orderPaymentSuccess(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        order.setPaymentStatus(PaymentStatus.SUCCESS);

        try {
            orderRepository.save(order);
            return String.format("Order %s successfully paid", order.getReference());
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public String orderPaymentFail(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        order.setPaymentStatus(PaymentStatus.FAILED);

        try {
            orderRepository.save(order);
            return String.format("Order %s pay failed", order.getReference());
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
