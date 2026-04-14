# Relayroom
Two browsers walk into a room. The server has no idea what they're talking about.

This project aims to sync a shared state between browsers using REST and WebSockets (STOMP over SockJS).

This is part of a backend for a [different project](https://github.com/1kulud/Quran).

# Table of Content
* [How it works](#how-it-works)
* [Configuration](#configuration)
* [Running locally](#running-locally)
* [Running with Docker](#running-with-docker)

# How it works
Each *room* is identified by a string ID, holding a payload `data`.

A client can update the room, and subscribers to the room's WebSocket topic are notified of the change.

## Tech Stack
 
- Java 21
- Spring Boot 3.5 (4 gave me pain in testing)
- Spring WebSocket (STOMP over SockJS)
- Maven

## API
| Method | Endpoint              | Description            |
|--------|-----------------------|------------------------|
| GET    | `/api/room/{roomId}`  | Get room data          |
| PUT    | `/api/room/{roomId}`  | Update room data       |

## WebSocket
Conenct to `/ws` via SockJS + STOMP and subscribe to `/topic/room/{roomId}`.

Now you get to receive updates whenever room's data changes.

# Configuration
| Property          | Env Variable       | Default                   | Description                    |
|-------------------|--------------------|---------------------------|--------------------------------|
| `server.port`     | `SERVER_PORT`      | `8080`                    | Port the server listens on     |
| `allowed.origins` | `ALLOWED_ORIGINS`  | `http://localhost:5500`   | Allowed CORS/WebSocket origin  |



---

# Running locally
Simply run:
```bash
./mvnw spring-boot:run
```

The backend server should start on port `8080` by default.

You can also test `GET`/`PUT` by running:
```bash
./mvnw test
```

# Running with Docker
```bash
docker-compose up --build
```

The configuration can be set inside the `.env` at the root of the project
```bash
SERVER_PORT=8080
ALLOWED_ORIGINS=http://localhost:5500
```
