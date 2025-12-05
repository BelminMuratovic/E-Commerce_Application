# E-Commerce Application

[![CI/CD Status](https://github.com/BelminMuratovic/E-Commerce_Application/actions/workflows/ci-cd.yaml/badge.svg)](https://github.com/BelminMuratovic/E-Commerce_Application/actions/workflows/ci-cd.yaml)

Full-stack e-commerce web application with automated CI/CD pipeline, built with Spring Boot and Angular.

## Tech Stack

### Backend

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Maven**

### Frontend

- **Angular**
- **TypeScript**

### DevOps

- **Docker**
- **Docker Compose**
- **GitHub Actions**

## Project Structure

```
.
├── ECommerce/              # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   ├── Dockerfile
│   └── pom.xml
├── ECommerce-FE/           # Angular frontend
│   ├── src/
│   └── Dockerfile
├── docker-compose.yaml     # Docker orchestration
└── .github/
    └── workflows/
        └── ci-cd.yaml      # CI/CD pipeline
```

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/BelminMuratovic/E-Commerce_Application.git
cd E-Commerce_Application
```

### 2. Start the application

```bash
docker compose up -d
```

### 3. Access the application

- **Frontend:** http://localhost:4200
- **Backend API:** http://localhost:8080
- **Database:** localhost:5433

### 4. Stop the application

```bash
docker compose down
```

## Development

### Local Development (without Docker)

#### Backend

```bash
cd ECommerce
mvn spring-boot:run
```

#### Frontend

```bash
cd ECommerce-FE
npm install
ng serve
```

### Database Setup

- Database: `ecommerce`
- Username: `postgres`
- Password: `password`
