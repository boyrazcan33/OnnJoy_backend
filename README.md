# 🛠️ Total Tech Stack

## 🎥 Demo Video

[![Watch the Demo](./assets/Screenshot_20250407_074318.png)](https://vimeo.com/1073099828)





## 📌 Backend – Java Spring Boot

### Core Technologies
- **Java 17** – Primary programming language  
- **Spring Boot 3.4.4** – Application framework  
- **Spring Security** – Authentication & authorization  
- **Spring Data JPA** – ORM and database abstraction  
- **Spring AI** – Integration with AI services (e.g., OpenAI)

### Database
- **PostgreSQL** – Relational database system  
- **JdbcTemplate** – Direct SQL query execution  
- **Hibernate** – ORM implementation via Spring Data JPA

### Authentication & Security
- **JWT (JSON Web Tokens)** – Stateless authentication  
- **BCrypt** – Secure password hashing

### Payment Integration
- **Stripe** – Payment gateway for checkout & subscriptions

### Other Libraries & Tools
- **Lombok** – Reduces boilerplate Java code  
- **JSON** – JSON data processing  
- **Spring Boot DevTools** – Live reload for development

### Architecture
- **RESTful API** – Backend exposes REST endpoints  
- **MVC Pattern** – Structured with Controllers, Services, Repositories  
- **DTOs** – Layered data transfer between frontend/backend  
- **Vector Similarity** – AI-based therapist-user matching using embeddings

---

## 💻 Frontend – Flutter

### Core Technologies
- **Flutter SDK 3.7+** – Cross-platform mobile framework  
- **Dart** – Programming language for Flutter apps

### State Management
- **Provider** – Dependency injection & state management

### Networking
- **HTTP** – REST API communication

### UI & UX
- **Material Design** – Standard UI components  
- **Custom Widgets** – Reusable UI components  
- **Table Calendar** – Appointment booking/calendar view

### Video Calling
- **Agora RTC Engine** – (Planned) for secure video sessions  
- **Voice Masking** – Optional voice privacy feature (in development)

### Auth & Storage
- **Shared Preferences** – Stores lightweight app settings  
- **Flutter Secure Storage** – Encrypted data storage

### Payments
- **URL Launcher** – Redirects users to Stripe checkout page

### Localization
- **Flutter Localizations** – Built-in i18n support  
- **Custom Translation System** – Translates UI into:
  - English 🇬🇧  
  - Estonian 🇪🇪  
  - Latvian 🇱🇻  
  - Lithuanian 🇱🇹  
  - Russian 🇷🇺

### Additional Libraries
- **Intl** – Date and number formatting  
- **Permission Handler** – Runtime permission requests

---

## ⚙️ DevOps & Scripting

### Python Utilities
- **Python** – Used for development tooling  
- **Faker** – Generates mock user/appointment data  
- **psycopg2** – PostgreSQL access via Python

---

## ✨ Key Features

1. **User Authentication**  
   - Secure login/signup with email & password  
   - JWT-based session handling  

2. **Internationalization**  
   - Multi-language UI (5 supported languages)  
   - User language preference saved locally  

3. **Therapist Matching**  
   - AI-powered matching with vector embeddings  
   - Intelligent text similarity scoring  

4. **Appointment Booking**  
   - Calendar interface with time slots  
   - Support for single, monthly, and intensive packages  

5. **Payment Integration**  
   - Seamless Stripe integration  
   - Tiered pricing options  

6. **Video Call Support**  
   - Built-in (planned) video sessions via Agora  
   - Optional voice masking for privacy  

7. **Notifications**  
   - In-app alerts and updates  
   - Read/unread status tracking  
