# OnnJoy Backend

OnnJoy is a mental wellness platform offering affordable, anonymous, and AI-enhanced therapy sessions. This repository contains the backend code for user and therapist management, AI-based therapist matching, session scheduling, and payment integration.

## Features

- Anonymous user sign-up with elegant username generation.
- Therapist profiles and multilingual bios.
- AI-driven therapist matching using text embeddings and cosine similarity.
- Appointment booking with calendar-based availability.
- Stripe payment integration with multiple package options.
- Session notifications and secure video links.
- Admin and therapist access control.

## Tech Stack

- Java 17
- Spring Boot 3
- PostgreSQL
- JWT Authentication (jjwt)
- Stripe API (Payments)
- Spring AI (OpenAI embeddings)
- Agora (Video sessions)
- Redis (  for caching)
- Deployed on Render / Railway / Vercel

## Project Structure

- `controller/` - REST API endpoints
- `service/` - Business logic
- `repository/` - JPA repositories
- `model/` - Entity classes
- `dto/` - Request/response models
- `config/` - Security and app configuration
- `util/` - Utility classes (e.g., vector math)

## Database

The app uses PostgreSQL with the following key tables:

- `users` – Anonymous user accounts
- `therapists` – Therapist credentials and info
- `therapist_bios` – Language-based bios
- `entries` – User entries for matching
- `matches` – AI-generated therapist matches
- `appointments`, `payments`, `notifications`, etc.


There is no properties due to KEYs
