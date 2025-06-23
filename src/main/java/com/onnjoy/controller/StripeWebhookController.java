package com.onnjoy.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
@RequestMapping("/api/webhook")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        String payload;
        try (InputStream inputStream = request.getInputStream(); Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            payload = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Unable to read request body");
        }

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Invalid Stripe signature");
        }

        // Log the event type for debuggging
        System.out.println("Stripe Event Received: " + event.getType());

        // Handle checkout.session.completed events
        if ("checkout.session.completed".equals(event.getType())) {
            // Deserialize the session object
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (session != null) {
                String sessionId = session.getId();
                String customerEmail = session.getCustomerDetails() != null
                        ? session.getCustomerDetails().getEmail()
                        : "unknown";

                // Add your logic here to mark appointment/payment in DB
                System.out.println("Checkout session completed. Session ID: " + sessionId);
                System.out.println("Customer email: " + customerEmail);

                // Example placeholder:
                // appointmentService.markAsPaidBySessionId(sessionId);
            }
        }

        return ResponseEntity.ok("Webhook processed");
    }
}
