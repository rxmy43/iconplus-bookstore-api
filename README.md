# Online Bookstore API

Technical Test Project for **Iconplus**  
Author: **Ramy Abyyu**  
Aspiring Backend Java Developer with strong focus on building reliable, secure, and clean backend systems.

---

## 📌 Overview

This is a RESTful backend service for an **Online Bookstore**, built with **Spring Boot** and **PostgreSQL**.  
The application implements authentication, authorization, book & category management, ordering, payment simulation, and reporting.

The system enforces **role-based access control**:

-   **Admin**: Full CRUD on categories & books, order management, reporting.
-   **User**: Browse books, place orders, simulate payment, view personal order history.

---

## 🚀 Features

### 1. Authentication & Authorization

-   `POST /register` → Register new user (password stored as hash).
-   `POST /login` → Login, returns **JWT Token**.
-   All other endpoints require JWT (`Authorization: Bearer <token>`).

### 2. Categories (Admin only)

-   `POST /categories`
-   `GET /categories`
-   `PUT /categories/{id}`
-   `DELETE /categories/{id}`

### 3. Books

-   `POST /books` (Admin, supports image in base64).
-   `GET /books` (User & Admin, supports pagination, search, filter by category).
-   `GET /books/{id}`
-   `PUT /books/{id}` (Admin).
-   `DELETE /books/{id}` (Admin).

### 4. Orders & Payment

-   `POST /orders` → Create order (multi-item, stock validation, total price calculation).
-   `POST /orders/{id}/pay` → Simulate payment (status: `PENDING` → `PAID`).
-   `GET /orders` → User: only own orders, Admin: all orders.
-   `GET /orders/{id}` → Order details (only owner or Admin).

### 5. Reporting (Admin only)

-   `GET /reports/sales` → Total revenue & total books sold.
-   `GET /reports/bestseller` → Top 3 best-selling books.
-   `GET /reports/prices` → Max, min, avg book price.

---

## 🛠️ Tech Stack

-   **Java 21**
-   **Spring Boot 3**
-   **PostgreSQL**
-   **Maven**
-   **JWT (JSON Web Token)**
-   **Swagger / OpenAPI**

---

## ⚙️ Setup & Installation

### 1. Clone Repository

```bash
git clone <your-repo-url>
cd online-bookstore
```

### 2. Configure Database

Ensure PostgreSQL is running and create a database:

```sql
CREATE DATABASE iconplus_bookstore_db;
```

### 3. Configuration

Sensitive configs (DB credentials, JWT secret, etc.) are stored in
`src/main/resources/application-local.yml`:

```yaml
spring:
    datasource:
        url: jdbc:postgresql://localhost:5432/iconplus_bookstore_db
        username: <your-username>
        password: <your-password>

jwt:
    secret: <your-secret-key>
    expiration: 86400000
    issuer: online-bookstore
```

> **Note**: This file is ignored from Git for security reasons.
> You must create your own `application-local.yml` locally before running.

---

## ▶️ Running the Application

Run with Maven, specifying the active profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Or build and run the jar:

```bash
mvn clean package -DskipTests
java -jar target/online-bookstore-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

---

## 📖 API Documentation

Swagger UI is available after running the app:

```
http://localhost:8080/swagger-ui.html
```

OpenAPI docs:

```
http://localhost:8080/api-docs
```

---

## 🧪 Testing

-   Postman collection or Swagger can be used for API testing.
-   All endpoints return **consistent JSON responses**.

---

## ✍️ Notes from the Author

This project was developed by **Ramy Abyyu**,
as part of a **Technical Test for Iconplus**.

My goal is to pursue a career as a **Backend Java Developer**,
focusing on **clean architecture, reliable systems, and secure APIs**.

---
