# ProductService
![CI](https://github.com/natiworks/Product-Service/actions/workflows/ci.yml/badge.svg)

## Tech Stack

| Technology              | Purpose |
|-------------------------|---|
| Java 21                 | Core language |
| Spring Boot 3.x         | Application framework |
| Spring Data JPA         | Database access via Hibernate |
| H2 (in-memory)          | Embedded database for dev/test |
| springdoc-openapi 2.8.6 | Swagger UI / OpenAPI docs |
| JUnit 5 + MockMvc       | Integration testing |
| GitHub Actions          | CI pipeline |

---

## Getting Started

### Run the application
```bash
mvn spring-boot:run
```
## Swagger UI Screenshot

![Swagger UI](docs/all%20five%20endpoints.png)
### Run the tests
```bash
mvn test
```

Expected test output:
```
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---


## Useful URLs

Once the application is running on port 8080:

| URL | Description |
|-----|-------------|
| http://localhost:8080/api/v1/products | Main API endpoint |
| http://localhost:8080/swagger-ui.html | Interactive Swagger UI docs |
| http://localhost:8080/api-docs | Raw OpenAPI 3.0 JSON spec |
| http://localhost:8080/h2-console | H2 in-memory database console |
| http://localhost:8080/health | Custom health check endpoint |
| http://localhost:8080/actuator/health | Spring Actuator health endpoint |

---

## Sample Requests

### Create a product (POST)
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Webcam","price":99.99,"stockQty":20,"category":"Peripherals"}'
```

### Get all products (GET)
```bash
curl http://localhost:8080/api/v1/products
```

### Update a product (PUT)
```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Webcam HD","price":119.99,"stockQty":15,"category":"Peripherals"}'
```

### Delete a product (DELETE)
```bash
curl -X DELETE http://localhost:8080/api/v1/products/1
```

---

## Error Responses

All errors follow RFC 9457 ProblemDetail format:

### 404 Not Found
```json
{
  "type": "https://api.example.com/errors/not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Product 99 not found"
}
```

### 400 Bad Request (validation failure)
```json
{
  "type": "https://api.example.com/errors/validation",
  "title": "Validation Error",
  "status": 400,
  "detail": "Name is required"
}
```

---

## Project Structure

```
src/main/java/com/ctbe/Natnaelnigatu/
├── ProductServiceApplication.java
├── model/
│   └── Product.java
├── dto/
│   ├── ProductRequest.java
│   └── ProductResponse.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── repository/
│   └── ProductRepository.java
├── service/
│   └── ProductService.java
└── controller/
    ├── ProductController.java
    └── HealthController.java

postman/
└── product-service-lab2.json
```

---

## CI Pipeline

This repository uses GitHub Actions. Every push to `main` automatically:
1. Checks out the source code
2. Sets up Java 21
3. Runs `mvn verify` (build + all tests)
4. Uploads Surefire test reports
5. Uploads JaCoCo coverage report

See [`.github/workflows/ci.yml`](.github/workflows/ci.yml) for the full pipeline configuration.