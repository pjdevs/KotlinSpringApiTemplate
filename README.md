# Kotlin Spring API Template

A simple but complete modern Kotlin Spring Boot API template following hexagonal architecture (ports & adapters).

## 🎯 What's inside?

- **Modern JVM stack** - Kotlin 2.3+ and JDK 25 
- **Hexagonal Architecture** - Clean separation: Domain → Ports → Adapters
- **Coroutines** - async code with Kotlin's `suspend` functions
- **Spring Boot 4.0** - Latest WebFlux reactive stack
- **Flyway Migrations** - Database versioning & seeding
- **OpenAPI/Swagger** - Auto-generated API docs at `/swagger-ui.html`
- **SQLite** - Zero-config database for quick prototyping
- **Versioning** - API versioning at path

## 🚀 Quick Start

```bash
./gradlew bootRun
```
API: http://localhost:8080/api/v1/

Swagger: http://localhost:8080/api/swagger-ui.html

## 📁 Architecture

```
api/      → REST controllers, Spring config, wiring of chosen adapters
domain/   → Business logic, use cases, ports (pure Kotlin)
infra/    → Database, external APIs, events (adapters)
```

### Why Hexagonal?

- ✅ Highly testable domain
- ✅ Easy to swap implementations
- ✅ Clear boundaries and dependencies
- ✅ Easy to read with clear contract on dependencies (ports)

## 🧪 Testing

```bash
./gradlew test
```

## 🔧 Tech Stack

- Kotlin 2.3 + JDK 25
- Spring Boot 4.0 (WebFlux)
- Flyway + SQLite
- JUnit 5 + Mockk
