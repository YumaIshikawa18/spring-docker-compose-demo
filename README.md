# spring-docker-compose-demo
DockerQuest is a simulation game where a hero gains experience by defeating randomly generated monsters. The hero levels up dynamically based on accumulated experience.

## Requirements & Setup

### Requirements

- Java 21
- Docker


### Build and Run

To build and run the project, execute:
```bash
  docker compose down --volumes && ./mvnw spring-boot:run
```
Access the hero details at:
```bash
  curl -X GET "http://localhost:8080/hero"
```
