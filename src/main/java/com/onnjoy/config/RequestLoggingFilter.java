package com.onnjoy.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log the authorization header for debugging
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            // Only print a part of it for security
            System.out.println("Auth header received: " +
                    (authHeader.length() > 20 ? authHeader.substring(0, 20) + "..." : authHeader));
        } else {
            System.out.println("No Authorization header in request to: " + request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }
}