# ITI Backend Challenge - Password Validator API

A robust REST API built with **Kotlin** and **Spring Boot** that validates passwords based on configurable security rules. This project demonstrates the implementation of **Clean Architecture** principles and **SOLID** design patterns.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Architecture & Design](#-architecture--design)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [Password Validation Rules](#-password-validation-rules)
- [Technologies](#-technologies)
- [How to Run](#-how-to-run)
- [Testing](#-testing)
- [Design Decisions](#-design-decisions)

---

## 🎯 Project Overview

This application exposes a web API that validates whether a password meets specific security criteria. The validation rules are stored in MongoDB and can be easily configured without code changes.

### Key Features:
- ✅ REST API for password validation
- ✅ Configurable validation rules via database
- ✅ Clean Architecture implementation
- ✅ SOLID principles applied throughout
- ✅ Comprehensive unit and integration tests
- ✅ OpenAPI/Swagger documentation
- ✅ Docker containerization with MongoDB

---

## 🏗️ Architecture & Design

This project follows **Clean Architecture** principles, separating concerns into distinct layers:

```
┌─────────────────────────────────────────────────────────┐
│                    ADAPTER LAYER                        │
│  (Controllers, DTOs, Configs, Exception Handlers)       │
│                                                         │
│  - AuthController                                       │
│  - HealthController                                     │
│  - GlobalExceptionHandler                               │
│  - OpenApiConfig                                        │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                  APPLICATION LAYER                      │
│        (Business Logic, Use Cases, Services)            │
│                                                         │
│  - IAuthService / AuthServiceImpl                       │
│  - PasswordValidatorUseCase implementations             │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                    DOMAIN LAYER                         │
│         (Entities, Repository Interfaces)               │
│                                                         │
│  - Parameterization (sealed class)                      │
│  - ParameterizationRepository                           │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                INFRASTRUCTURE LAYER                     │
│       (Database configs, Migrations, External)          │
│                                                         │
│  - MongockConfig                                        │
│  - CreatePasswordValidationParameterizations            │
└─────────────────────────────────────────────────────────┘
```

### SOLID Principles Applied:

1. **Single Responsibility Principle (SRP)**
   - Each use case validator handles one specific validation rule
   - Controllers only handle HTTP concerns
   - Services coordinate business logic without knowing implementation details

2. **Open/Closed Principle (OCP)**
   - New password validation rules can be added by creating new `PasswordValidatorUseCase` implementations
   - No need to modify existing code to add new validators

3. **Liskov Substitution Principle (LSP)**
   - All use case implementations can be substituted for the `PasswordValidatorUseCase` interface
   - Parameterization types (Int, List<String>) extend the sealed class correctly

4. **Interface Segregation Principle (ISP)**
   - Small, focused interfaces like `IAuthService` and `PasswordValidatorUseCase`
   - Clients depend only on the methods they use

5. **Dependency Inversion Principle (DIP)**
   - High-level modules (controllers) depend on abstractions (interfaces)
   - Dependencies are injected via constructor (Spring's dependency injection)
   - Repository interfaces in domain layer, implementations would be in infrastructure

---

## 📁 Project Structure

```
iti-backend-challenge/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/
│   │   │       └── iti/
│   │   │           └── backend_challenge/
│   │   │               ├── BackendChallengeApplication.kt
│   │   │               ├── adapter/                         # Presentation Layer
│   │   │               │   ├── configs/
│   │   │               │   │   ├── GlobalExceptionHandler.kt
│   │   │               │   │   └── OpenApiConfig.kt
│   │   │               │   ├── controllers/
│   │   │               │   │   ├── AuthController.kt        # Password validation endpoint
│   │   │               │   │   └── HealthController.kt      # Health check endpoint
│   │   │               │   ├── dtos/
│   │   │               │   │   ├── ErrorResponse.kt
│   │   │               │   │   ├── HealthCheckResponse.kt
│   │   │               │   │   ├── ValidatePasswordRequest.kt
│   │   │               │   │   └── ValidatePasswordResponse.kt
│   │   │               │   └── exceptions/
│   │   │               │       └── InternalServerErrorException.kt
│   │   │               ├── application/                     # Business Logic Layer
│   │   │               │   ├── exceptions/
│   │   │               │   │   └── ParameterizationNotFoundException.kt
│   │   │               │   ├── services/
│   │   │               │   │   ├── IAuthService.kt
│   │   │               │   │   └── impl/
│   │   │               │   │       └── AuthServiceImpl.kt
│   │   │               │   └── useCases/
│   │   │               │       ├── PasswordValidatorUseCase.kt
│   │   │               │       └── impl/
│   │   │               │           └── PasswordValidatorUseCaseImpl.kt  # All validators
│   │   │               ├── domain/                          # Domain Layer
│   │   │               │   ├── entities/
│   │   │               │   │   └── Parameterization.kt      # Sealed class for config
│   │   │               │   └── repositories/
│   │   │               │       └── ParameterizationRepository.kt
│   │   │               └── infrastructure/                  # Infrastructure Layer
│   │   │                   ├── configs/
│   │   │                   │   └── MongockConfig.kt
│   │   │                   └── migrations/
│   │   │                       └── CreatePasswordValidationParameterizations.kt
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-docker.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       ├── kotlin/
│       │   └── com/
│       │       └── iti/
│       │           └── backend_challenge/
│       │               ├── BackendChallengeApplicationTests.kt
│       │               ├── integration/
│       │               │   └── ValidatePasswordIntegrationTest.kt
│       │               └── unit/
│       │                   └── application/
│       │                       ├── services/
│       │                       │   └── AuthServiceImplUnitTest.kt
│       │                       └── useCases/
│       │                           └── PasswordValidatorUseCaseImplUnitTest.kt
│       └── resources/
│           └── application-test.properties
├── build.gradle.kts
├── compose.yaml
├── Dockerfile
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
├── CHALLENGE.md
└── README.md
```

---

## 🔌 API Endpoints

### 1. **POST** `/auth/validate-password`
Validates a password against the configured security rules.

**Request Body:**
```json
{
  "password": "AbTp9!fok"
}
```

**Response (200 OK):**
```json
{
  "is_valid": true
}
```

**Example - Valid Password:**
```bash
curl -X POST http://localhost:8080/auth/validate-password \
  -H "Content-Type: application/json" \
  -d '{"password": "AbTp9!fok"}'
```

**Example - Invalid Password:**
```bash
curl -X POST http://localhost:8080/auth/validate-password \
  -H "Content-Type: application/json" \
  -d '{"password": "weak"}'
```

### 2. **GET** `/health-check`
Returns the health status of the application.

**Response (200 OK):**
```json
{
  "status": "OK"
}
```

### 📚 API Documentation
The complete API documentation is available via Swagger UI:
- **URL:** http://localhost:8080/swagger-ui/index.html

---

## 🔐 Password Validation Rules

A password is considered valid when it meets **ALL** of the following criteria:

| Rule                   | Requirement                                      |
|------------------------|--------------------------------------------------|
| **No Empty**           | It cannot be empty                               |
| **Minimum Length**     | 9 or more characters                             |
| **Digits**             | At least 1 digit (0-9)                           |
| **Lowercase**          | At least 1 lowercase letter (a-z)                |
| **Uppercase**          | At least 1 uppercase letter (A-Z)                |
| **Special Characters** | At least 1 special character from: `!@#$%^&*()-+` |
| **No Repetition**      | No repeated characters anywhere in the password  |
| **No Spaces**          | Whitespace characters are not allowed            |

### Validation Examples:

| Password | Valid? | Reason |
|----------|--------|--------|
| `""` | ❌ | Empty |
| `aa` | ❌ | Too short, repeated 'a' |
| `ab` | ❌ | Too short |
| `AAAbbbCc` | ❌ | No digit, no special char, repeated chars |
| `AbTp9!foo` | ❌ | Repeated 'o' |
| `AbTp9!foA` | ❌ | Repeated 'A' |
| `AbTp9 fok` | ❌ | Contains space |
| `AbTp9!fok` | ✅ | Meets all criteria |

### Configuration via Database

Validation rules are stored in MongoDB as `Parameterization` entities:
- `MIN_CHARS`: Minimum character count (default: 9)
- `MIN_DIGITS`: Minimum number of digits (default: 1)
- `MIN_LOWERCASE`: Minimum lowercase letters (default: 1)
- `MIN_UPPERCASE`: Minimum uppercase letters (default: 1)
- `MIN_SPECIAL_CHARS`: Minimum special characters (default: 1)
- `ALLOWED_SPECIAL_CHARS`: List of allowed special characters

These can be modified in the database without redeploying the application.

---

## 🛠️ Technologies

- **Language:** Kotlin 2.2.21
- **Framework:** Spring Boot 4.0.2
- **Database:** MongoDB 6
- **Migration:** Mongock 5.4.4
- **Documentation:** SpringDoc OpenAPI 2.7.0
- **Build Tool:** Gradle 8.x
- **Containerization:** Docker & Docker Compose
- **Testing:**
  - JUnit 5
  - Mockito Kotlin 5.1.0
  - Testcontainers 1.19.3
- **JVM:** Java 21

---

## 🚀 How to Run

### Prerequisites
- **Docker** installed on your machine
- **Docker Compose** installed

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd iti-backend-challenge
   ```

2. **Run with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

   This command will:
   - Build the application Docker image
   - Start MongoDB container
   - Start the application container
   - Run database migrations automatically
   - Expose the API on port 8080

3. **Access the API:**
   - **Swagger UI:** http://localhost:8080/swagger-ui/index.html
   - **Health Check:** http://localhost:8080/health-check

4. **Stop the application:**
   ```bash
   docker-compose down
   ```

### Running Locally (without Docker)

If you want to run the application locally for development:

1. **Start MongoDB:**
   ```bash
   docker run -d -p 27017:27017 \
     -e MONGO_INITDB_ROOT_USERNAME=root \
     -e MONGO_INITDB_ROOT_PASSWORD=secret \
     -e MONGO_INITDB_DATABASE=backend-challenge \
     mongo:6
   ```

2. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

3. **Run tests:**
   ```bash
   ./gradlew test
   ```

---

## 🧪 Testing

The project includes comprehensive test coverage with **unit tests** and **integration tests**.

### Test Structure

```
test/
├── unit/
│   ├── application/
│   │   ├── services/
│   │   │   └── AuthServiceImplUnitTest.kt        # Tests for AuthService
│   │   └── useCases/
│   │       └── PasswordValidatorUseCaseImplUnitTest.kt  # Tests for each validator
└── integration/
    └── ValidatePasswordIntegrationTest.kt        # End-to-end API tests
```

### Unit Tests

#### **PasswordValidatorUseCaseImplUnitTest**
Tests each individual validation rule in isolation:
- `IsThePasswordIsNotEmptyUseCase` - Validates non-empty passwords
- `IsTheCharacterCountValid` - Validates minimum character count
- `IsTheQuantityOfNumbersValid` - Validates digit requirements
- `IsTheNumberOfUppercaseCharactersValid` - Validates uppercase requirements
- `IsTheNumberOfLowercaseCharactersValid` - Validates lowercase requirements
- `IsTheNumberOfSpecialCharactersValid` - Validates special character requirements
- `IsTheNumberOfRepeatedCharactersValid` - Ensures no repeated characters
- `IsThePasswordHasNoSpacesUseCase` - Ensures no whitespace

Each test:
- ✅ Tests valid scenarios (should pass validation)
- ✅ Tests invalid scenarios (should fail validation)
- ✅ Tests edge cases
- ✅ Tests exception handling for missing parameterizations

#### **AuthServiceImplUnitTest**
Tests the service layer that coordinates all validators:
- ✅ Successful validation with all rules passing
- ✅ Validation failure for each specific rule
- ✅ Exception handling
- ✅ Repository interaction mocking

### Integration Tests

#### **ValidatePasswordIntegrationTest**
End-to-end tests using Testcontainers:
- ✅ Full application context startup
- ✅ Real MongoDB database (via Testcontainers)
- ✅ HTTP request/response testing
- ✅ Complete validation flow

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "AuthServiceImplUnitTest"

# Run tests with coverage report
./gradlew test jacocoTestReport
```

### Test Results

After running tests, view the HTML report:
```
build/reports/tests/test/index.html
```

---

## 💡 Design Decisions

### 1. **Clean Architecture**
The application is structured in layers with clear boundaries:
- **Adapter Layer:** Handles external concerns (HTTP, DTOs)
- **Application Layer:** Contains business logic and use cases
- **Domain Layer:** Core entities and repository interfaces
- **Infrastructure Layer:** Database configuration and migrations

**Benefit:** Each layer is independent and can be tested/modified without affecting others.

### 2. **Use Case Pattern for Validators**
Each validation rule is implemented as a separate use case component:
```kotlin
interface PasswordValidatorUseCase {
    fun isValidatePassword(password: String, parameters: List<Parameterization>): Boolean
}
```

**Benefits:**
- ✅ Easy to add new validation rules (Open/Closed Principle)
- ✅ Each validator has a single responsibility
- ✅ Highly testable in isolation
- ✅ Can be enabled/disabled via dependency injection

### 3. **Database-Driven Configuration**
Validation parameters are stored in MongoDB rather than hardcoded:

**Benefits:**
- ✅ Rules can be changed without code deployment
- ✅ Different environments can have different rules
- ✅ Historical tracking of rule changes
- ✅ A/B testing capabilities

### 4. **Sealed Class for Parameterization**
```kotlin
sealed class Parameterization(...)
class ParameterizationIntType(...) : Parameterization(...)
class ParameterizationListOfStringType(...) : Parameterization(...)
```

**Benefits:**
- ✅ Type-safe parameter handling
- ✅ Exhaustive when-expressions in Kotlin
- ✅ Easy to add new parameter types
- ✅ Better than using untyped maps

### 5. **Mongock for Database Migrations**
Using Mongock ensures:
- ✅ Version-controlled database changes
- ✅ Automatic initialization on first run
- ✅ Idempotent migrations
- ✅ Easy rollback capabilities

### 6. **Comprehensive Testing Strategy**
- **Unit Tests:** Fast, isolated tests for each component
- **Integration Tests:** End-to-end tests with real dependencies (via Testcontainers)
- **Mocking:** Strategic use of mocks for dependencies

### 7. **OpenAPI/Swagger Documentation**
Automatic API documentation generation:
- ✅ Always in sync with code
- ✅ Interactive testing via Swagger UI
- ✅ Schema validation

### 8. **Exception Handling**
Global exception handler provides consistent error responses across the API.

---

## 👤 Author

Waldyr Turquetti