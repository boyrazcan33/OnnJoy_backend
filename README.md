TOTAL TECH STACK
Backend (Java Spring Boot)
Core Technologies
•	Java 17 - The programming language used for the backend
•	Spring Boot 3.4.4 - Framework for building Java applications
•	Spring Security - For authentication and authorization
•	Spring Data JPA - For database access and ORM
•	Spring AI - Integration with AI services (OpenAI)
Database
•	PostgreSQL - Relational database system
•	JdbcTemplate - Used for direct SQL queries
•	Hibernate - ORM implementation (via Spring Data JPA)
Authentication
•	JWT (JSON Web Tokens) - For stateless authentication
•	BCrypt - For password hashing
Payment Processing
•	Stripe - Payment gateway integration
Other Libraries
•	Lombok - For reducing boilerplate code
•	JSON - For JSON processing
•	Spring Boot DevTools - For development productivity
Architecture
•	REST API - Backend exposes RESTful endpoints
•	MVC Pattern - Controllers, Services, Repositories
•	DTOs - For data transfer between layers
•	Vector Similarity - For matching users with therapists based on embeddings
Frontend (Flutter)
Core Technologies
•	Flutter SDK 3.7+ - Cross-platform UI framework
•	Dart - Programming language for Flutter
State Management
•	Provider - For state management and dependency injection
Networking
•	HTTP - For REST API calls
UI Components
•	Material Design - Core UI components
•	Custom Widgets - Custom-built components for the app
•	Table Calendar - For calendar functionality
Video Calling
•	Agora RTC Engine - For video call functionality(unconnected)
Authentication & Storage
•	Shared Preferences - For storing app preferences
•	Flutter Secure Storage - For secure storage of sensitive information
Payments
•	URL Launcher - For redirecting to Stripe checkout
Localization
•	Flutter Localizations - For internationalization
•	Custom Translation System - For supporting multiple languages (English, Estonian, Lithuanian, Latvian, Russian)
Other Libraries
•	Intl - For date/time formatting
•	Permission Handler - For handling device permissions
DevOps/Infrastructure
The project appears to include some Python scripts for generating fake data, suggesting:
•	Python - For data generation scripts
•	Faker - For generating fake data
•	psycopg2 - For connecting to PostgreSQL
Key Features
1.	User Authentication 
o	JWT-based authentication
o	Email/password login and signup
2.	Internationalization 
o	Support for 5 languages
o	Language preference storage
3.	Therapist Matching 
o	AI-powered matching using vector embeddings
o	Match scoring based on text similarity
4.	Appointment Booking 
o	Calendar-based booking
o	Package selection (single, monthly, intensive)
5.	Payment Processing 
o	Stripe integration for checkout
o	Different pricing tiers
6.	Video Calls 
o	Agora RTC for video sessions
o	Voice masking option
7.	Notifications 
o	In-app notification system
o	Read/unread status
