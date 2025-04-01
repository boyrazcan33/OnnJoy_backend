package com.onnjoy.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${app.frontend.success-url}")
    private String successUrl;

    @Value("${app.frontend.cancel-url}")
    private String cancelUrl;

    public String createCheckoutSession(Long userId, Long appointmentId, String packageType, int amountInCents) {
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur")
                                                .setUnitAmount((long) amountInCents) // e.g. 2900 = €29
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("OnnJoy - " + packageType + " session")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("user_id", String.valueOf(userId))
                .putMetadata("appointment_id", String.valueOf(appointmentId))
                .build();

        try {
            Session session = Session.create(params);
            return session.getUrl(); // Frontend redirects here
        } catch (Exception e) {
            throw new RuntimeException("Stripe session failed: " + e.getMessage());
        }
    }
}
