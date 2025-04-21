package com.tbond.yumdash.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tbond.yumdash.domain.Order;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Value("${stripe.secret-key}")
    private String secretKey;

    public String createPaymentLink(Order order) {
        Stripe.apiKey = secretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/v1/orders/success/" + order.getId())
                .setCancelUrl("http://localhost:8080/api/v1/orders/failed/" + order.getId())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount((long) order.getTotalPrice() * 100)
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Yum Dash")
                                                .build())
                                        .build()
                        )
                        .build())
                .build();

        try {
            Session session = Session.create(params);

            return session.getUrl();

        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
