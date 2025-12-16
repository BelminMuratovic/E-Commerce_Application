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

## Features

- CRUD operations for products and orders
- Shopping cart functionality
- Product image upload
- CI/CD pipeline with automated tests and Docker builds

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
├── logs                    # Backend logs
├── scripts                 # Scripts for setup and cleanup
├── docker-compose.yaml     # Docker orchestration
├── Makefile
└── .github/
    └── workflows/
        └── ci-cd.yaml      # CI/CD pipeline
```

## Configuration

The application uses environment variables for configuration. These are managed through a `.env` file.

### Environment Variables

Create a `.env` file in the root directory:

```bash
cp .env.example .env
```

Available variables:

| Variable            | Description           | Default     |
| ------------------- | --------------------- | ----------- |
| `POSTGRES_DB`       | Database name         | `ecommerce` |
| `POSTGRES_USER`     | Database username     | `postgres`  |
| `POSTGRES_PASSWORD` | Database password     | `password`  |
| `POSTGRES_PORT`     | Database port         | `5432`      |
| `BACKEND_PORT`      | Backend service port  | `8080`      |
| `FRONTEND_PORT`     | Frontend service port | `4200`      |

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/BelminMuratovic/E-Commerce_Application.git
cd E-Commerce_Application
```

### 2. Start the application

```bash
./scripts/setup.sh
```

or

```bash
make up
```

### 3. Access the application

- **Frontend:** http://localhost:4200
- **Backend API:** http://localhost:8080
- **Database:** localhost:5432

### 4. Stop the application

```bash
./scripts/cleanup.sh
```

or

```bash
make down
```

### View logs

```bash
make logs
```

or

```bash
tail -f logs/application.log
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

## Testing

### Backend Tests

- **Unit tests**: Service layer
- **Controller tests**: Endpoint behavior with mocked services (WebMvcTest)
- **Integration test**: Health check endpoint, end-to-end validation

### CI/CD Testing

All tests run automatically in the CI pipeline:

- Unit tests verify business logic
- Controller tests validate API endpoints with mocked services
- Integration test ensures service availability

## DevOps Implementation

### Containerization

- Docker Compose for multi-service orchestration
- Separate Dockerfiles for backend (Java 21) and frontend (Node.js)

### CI/CD Pipeline

- Automated testing and build validation
- Docker image building and service verification
- Deployment simulation in pipeline

### CI/CD Overview

- CI runs automated tests and builds Docker images.
- CD is simulated by starting the services and verifying application health.
