# ShrinkURL Backend

ShrinkURL is the backend service behind the [live Shrink url app](https://shrinkurl-nine.vercel.app/). It accepts long URLs & alias (generates one if not provided) and returns a short url.
The short url redirects visitors to the original destination using a fast Redis-backed lookup path with PostgreSQL as source of truth.

This repository contains the Java/Spring Boot API for the project. The frontend lives in a separate repository: [Nikhilnama18/short-url-frontend](https://github.com/Nikhilnama18/short-url-frontend).


## Built By

Built by **Nikhil Nama**.

- X (Twitter): [@Nick_1807](https://x.com/Nick_1807)
- LinkedIn: [nikhilnama18](https://www.linkedin.com/in/nikhilnama18/)

## Live Project

- Live app: [https://shrinkurl-nine.vercel.app/](https://shrinkurl-nine.vercel.app/)
- Frontend repo: [Nikhilnama18/short-url-frontend](https://github.com/Nikhilnama18/short-url-frontend)
- Backend repo: [Nikhilnama18/short-url](https://github.com/Nikhilnama18/short-url)

## What This Backend Does

- Creates shortened URLs from long URLs
- Supports user-provided custom aliases
- Generates aliases automatically using a Snowflake-style ID generator + Base62 encoding
- Redirects short links with HTTP `302 Found`
- Caches alias lookups in Redis to reduce repeated database reads
- Persists URL records in PostgreSQL via Spring Data JPA

## Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Data Redis
- PostgreSQL
- Redis
- Lombok
- Maven

### Infrastructure and Deployment

- Frontend hosting: [Vercel](https://vercel.com/)
- Backend hosting: [Render](https://render.com/)
- Cache: [Redis Cloud](https://cloud.redis.io)
- Containerization: Docker

## Architecture Overview

The application follows a simple request flow:

1. The frontend sends a request to create a short URL.
2. The backend stores the alias and original URL in PostgreSQL.
3. When someone opens a short link, the backend checks Redis first.
4. If the alias is not cached, the backend loads it from PostgreSQL and stores it in Redis for future requests.
5. The API responds with an HTTP redirect to the original long URL.

## Project Structure

```text
src/main/java/com/nikhil/shortURL
|- config/        Spring beans and app configuration
|- controller/    REST endpoints
|- dto/           Request/response payloads
|- entity/        JPA entities
|- exceptions/    Custom exceptions and global handler
|- repository/    PostgreSQL and Redis data access
|- service/       Core URL shortening and lookup logic
|- utils/         Snowflake ID and Base62 helpers
```

## API Endpoints

### Create a short URL

`POST /api/url`

Request body:

```json
{
  "longURL": "https://example.com/some/very/long/path",
  "alias": "example123"
}
```

`alias` is optional. If it is omitted, the backend generates one automatically.

Example response:

```json
{
  "longURL": "https://example.com/some/very/long/path",
  "shortURL": "http://localhost:8080/example123",
  "alias": "example123"
}
```

### Redirect to the original URL

`GET /{alias}`

Example:

```text
GET /example123
```

Response:

- `302 Found`
- `Location: <original-long-url>`

## Environment Variables

The application is configured through environment variables with sensible local defaults in `application.properties`.

| Variable | Purpose | Default |
| --- | --- | --- |
| `PORT` | Server port | `8080` |
| `APP_HOST_URL` | Base URL used to build short links | `http://localhost:8080` |
| `APP_SNOWFLAKE_MACHINE_ID` | Machine ID for alias generation | `1` |
| `DATASOURCE_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:5432/shorturl` |
| `DATASOURCE_USERNAME` | PostgreSQL username | `postgres` |
| `DATASOURCE_PASSWORD` | PostgreSQL password | empty |
| `DB_POOL_SIZE` | Hikari pool size | `5` |
| `REDIS_HOST` | Redis host | `localhost` |
| `REDIS_PORT` | Redis port | `6379` |
| `REDIS_USERNAME` | Redis username | `default` |
| `REDIS_PASSWORD` | Redis password | empty |
| `REDIS_SSL` | Redis SSL toggle | `false` |

## Running Locally

### Prerequisites

- Java 21
- Maven or the included Maven wrapper
- PostgreSQL
- Redis

### Start the app

```bash
./mvnw spring-boot:run
```

Or build the jar first:

```bash
./mvnw clean package
java -jar target/*.jar
```

Before starting the app, make sure PostgreSQL and Redis are available and your environment variables are set if you are not using the defaults.

## Running Tests

```bash
./mvnw test
```

The repository currently includes a basic Spring context load test. Because it boots the full application context, it expects the configured PostgreSQL connection to be reachable when tests run.

## Deployment

### Frontend

- Deployed on [Vercel](https://vercel.com/)
- Live URL: [https://shrinkurl-nine.vercel.app/](https://shrinkurl-nine.vercel.app/)

### Backend

- Deployed on [Render](https://render.com/)
- Render blueprint included in [`render.yaml`](./render.yaml)
- Docker-based deployment using the included [`Dockerfile`](./Dockerfile)

### Caching

- Hosted on [Redis Cloud](https://cloud.redis.io)


## Notes

- PostgreSQL is the primary data store.
- Redis is used as a cache for alias-to-URL lookups.
- Generated aliases are based on a Snowflake-style ID generator encoded in Base62.
