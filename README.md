# BFHL API — Bajaj Finserv Health Challenge

REST API built with **Spring Boot 3** and **Java 25** for the Bajaj Finserv Health coding challenge.

## API Endpoint

| Method | Route  | Description                  |
|--------|--------|------------------------------|
| POST   | /bfhl  | Process data array input     |

## Sample Request

```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a","1","334","4","R","$"]}'
```

## Sample Response

```json
{
  "is_success": true,
  "user_id": "chhavi_joshi_17122005",
  "email": "cj5766@srmist.edu.in",
  "roll_number": "RA2311003010629",
  "even_numbers": ["334", "4"],
  "odd_numbers": ["1"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

## Build & Run

```bash
# Build
mvnd clean package

# Run with Maven
mvnd spring-boot:run

# Run JAR directly
java -jar target/bfhl-api-0.0.1-SNAPSHOT.jar

# Run tests
mvnd test
```

## Configuration

Edit `src/main/resources/application.yml`:

```yaml
server:
  port: ${PORT:8080}

candidate:
  email: cj5766@srmist.edu.in
  rollNumber: RA2311003010629
```

## Deployment (Railway)

This project is Railway-ready:
- Server port reads from `PORT` environment variable
- Executable JAR via Spring Boot Maven Plugin
- No additional configuration needed

Set the build command to `mvnd clean package -DskipTests` and start command to `java -jar target/bfhl-api-0.0.1-SNAPSHOT.jar`.
