package com.onnjoy.controller;

import com.onnjoy.service.PaymentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckout(@RequestBody PaymentRequest request) {
        int amount = switch (request.packageType) {
            case "single" -> 2900;
            case "monthly" -> 7900;
            case "intensive" -> 12900;
            default -> throw new IllegalArgumentException("Unknown package type");
        };

        String checkoutUrl = paymentService.createCheckoutSession(
                request.userId,
                request.appointmentId,
                request.packageType,
                amount
        );

        return ResponseEntity.ok(checkoutUrl);
    }

    @Data
    public static class PaymentRequest {
        private Long userId;
        private Long appointmentId;
        private String packageType;
    }
}
