# application_pratique3

**Spring Cloud Microservices**

This repository contains a multi-module Spring Boot microservices system built using Gradle with Kotlin DSL. The project demonstrates service discovery, centralized configuration, and API gateway routing in a distributed environment using Spring Cloud.

The application models a simplified e-commerce backend composed of independently deployable services coordinated through Eureka and exposed via a reactive API Gateway.

---

## Objectives

The project is designed to illustrate the following concepts:

* Microservice decomposition with independent Spring Boot applications
* Runtime service discovery using Eureka
* Centralized, externalized configuration via Spring Cloud Config
* Gateway-based request routing and abstraction
* Health and readiness monitoring using Spring Boot Actuator
* Gradle multi-module builds with Kotlin DSL

---

## High-level architecture

```
                        ┌────────────────────────┐
                        │   Config Service       │
                        │     (config-svc)       │
                        │        :9999           │
                        └───────────┬────────────┘
                                    │
                                    │ optional config import
                                    ▼
 Client (HTTP / REST)        ┌────────────────────────┐
        │                    │      API Gateway       │
        └──────────────────►│     (gateway-svc)      │
                             │        :8888          │
                             └───────────┬────────────┘
                                         │ lb:// via Eureka
                                         ▼
                             ┌────────────────────────┐
                             │   Discovery Service    │
                             │   (discovery-svc)      │
                             │        :8761           │
                             └───────┬───────┬────────┘
                                     │       │
                                     ▼       ▼
                     ┌────────────────┐   ┌────────────────┐
                     │ customer-svc   │   │ inventory-svc  │
                     │     :8081      │   │     :8082      │
                     └────────────────┘   └────────────────┘
                                     │
                                     ▼
                            ┌────────────────┐
                            │  billing-svc   │
                            │     :8083      │
                            └────────────────┘
```

---

## Services overview

### Discovery Service (`discovery-svc`)

* Eureka Server used for service registration and lookup.
* Enables dynamic routing and loose coupling between services.

**Port:** 8761
**UI:** `http://localhost:8761`

---

### Config Service (`config-svc`)

* Spring Cloud Config Server for centralized configuration.
* Supports Git-backed or local (native) configuration repositories.

**Port:** 9999

Services import configuration using:

```
spring.config.import=optional:configserver:http://localhost:9999
```

---

### API Gateway (`gateway-svc`)

* Reactive gateway built with Spring Cloud Gateway.
* Single entry point for all external requests.
* Routes traffic dynamically using Eureka service IDs.

**Port:** 8888

Example routes:

* `/api/customers/**` → `customer-svc`
* `/api/products/**` → `inventory-svc`
* `/api/billing/**` → `billing-svc`

---

### Customer Service (`customer-svc`)

* Manages customer data.
* Uses Spring Data REST and JPA.
* Backed by an in-memory H2 database.

**Port:** 8081
**Base path:** `/api/customers`

---

### Inventory Service (`inventory-svc`)

* Manages product and catalog information.
* Uses JPA with H2 for persistence.

**Port:** 8082
**Base path:** `/api/products`

---

### Billing Service (`billing-svc`)

* Handles billing and payment-related data.
* Configuration (JDBC, H2 settings) provided via Config Server.

**Port:** 8083
**Base path:** `/api/billing`

---

## Technology stack

* Java 17
* Spring Boot 3.5.x
* Spring Cloud 2025.0.x
* Spring Cloud Netflix Eureka
* Spring Cloud Config
* Spring Cloud Gateway (Reactive)
* Spring Data JPA & Spring Data REST
* H2 in-memory database
* Gradle (Kotlin DSL)
* Spring Boot Actuator

---

## Repository structure

```
application_pratique3
│
├── build.gradle.kts
├── settings.gradle.kts
│
├── discovery-svc/
│   └── build.gradle.kts
├── config-svc/
│   └── build.gradle.kts
├── gateway-svc/
│   └── build.gradle.kts
├── customer-svc/
│   └── build.gradle.kts
├── inventory-svc/
│   └── build.gradle.kts
├── billing-svc/
│   └── build.gradle.kts
│
├── config-repo/
└── README.md
```

Each service is a standalone Gradle module and can be built or executed independently.

---

## Configuration management

The `config-repo` directory contains centralized configuration files such as:

* `application.properties`
* `customer-svc.properties`
* `inventory-svc.properties`
* `billing-svc.properties`

To use the local configuration repository, enable the native backend in `config-svc`:

```
spring.profiles.active=native
spring.cloud.config.server.native.search-locations=file:./config-repo
```

---

## Ports and endpoints

| Service   | Port | Example endpoint       |
| --------- | ---- | ---------------------- |
| Discovery | 8761 | `/`                    |
| Config    | 9999 | `/application/default` |
| Gateway   | 8888 | `/api/customers`       |
| Customer  | 8081 | `/api/customers`       |
| Inventory | 8082 | `/api/products`        |
| Billing   | 8083 | `/api/billing`         |

All services expose Actuator endpoints:

```
/actuator/health
```

---

## Build instructions (Gradle)

### Requirements

* Java 17
* Gradle Wrapper

### Build all services

```bash
./gradlew clean build -x test
```

Windows:

```cmd
gradlew.bat clean build -x test
```

---

### Build a single service

```bash
./gradlew :customer-svc:build -x test
```

---

## Running the system

Start each service in a separate terminal in the following order:

```bash
./gradlew :discovery-svc:bootRun
./gradlew :config-svc:bootRun
./gradlew :customer-svc:bootRun
./gradlew :inventory-svc:bootRun
./gradlew :billing-svc:bootRun
./gradlew :gateway-svc:bootRun
```

The system remains functional even if the Config Service is not running (configuration import is optional).

---

## Example requests

Via Gateway:

```
GET http://localhost:8888/api/customers
GET http://localhost:8888/api/products
GET http://localhost:8888/api/billing
```

Direct access:

```
GET http://localhost:8081/api/customers
GET http://localhost:8082/api/products
GET http://localhost:8083/api/billing
```

---

## Common pitfalls

* Gateway routing issues usually indicate a mismatch between service IDs and Eureka registrations.
* If configuration is not loaded, verify the Config Service is reachable or that local properties exist.
* Port conflicts can be resolved by overriding `server.port` per service.
* H2 console access depends on service-level configuration.

---

## Extensions and improvements

* Containerize services and add Docker Compose
* Secure gateway and services with Spring Security
* Introduce resilience patterns (timeouts, retries, circuit breakers)
* Add distributed tracing and centralized logging
* Implement integration tests using Testcontainers