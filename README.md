# Workout Tracker API

A RESTful fitness logging API built with Java and Spring Boot, designed with clean architecture and UI-friendly grouped JSON responses (similar to Hevy or Strong).

## Tech Stack
* Java 17
* Spring Boot 3
* Spring Data JPA / Hibernate
* MySQL 8
* Maven

## Getting Started

1. Set up environment variables:
```bash
cp .env.example .env
```

2. Start the database using Docker:
```bash
docker run --name workout-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=workout_db -p 3306:3306 -d mysql:8
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

## Core Endpoints

* `GET /api/v1/exercises` - List exercises
* * `GET /api/v1/exercises?muscleGroup=CHEST` - Filter exercises by muscle group (e.g., `CHEST`, `BACK`, `LEGS`, `ARMS`, `SHOULDERS`)
* `POST /api/v1/users` - Register a new user
* `POST /api/v1/routines` - Create a routine template
* `GET /api/v1/routines/user/{id}` - Get routines by user
* `POST /api/v1/workouts` - Log a completed workout
* `POST /api/v1/workouts/start-from-routine/{id}` - Start a workout from a template
* `GET /api/v1/workouts/user/{id}` - Fetch grouped workout history