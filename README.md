# TherapyPulse AI

RAG platform for clinical guidelines and protocols, built with Spring Boot 4, Spring Cloud, Spring AI and Ollama.

Scope: Epic 1 (RAG), Story 1 – Static ingestion resource

## Modules

| Module         | Port | What it does                                       |
|----------------|------|----------------------------------------------------|
| eureka-server  | 8761 | Service registry                                   |
| api-gateway    | 8080 | Single public entry point, routes via Eureka       |
| ingest-static  | 8081 | PDF ingestion from `./resources` (Epic 1, Story 1) |
| ingest-dynamic | 8082 | Internet ingestion (not in scope yet)              |
| retrieval-pre  | 8083 | Pre-retrieval pipeline (not in scope yet)          |
| retrieval-post | 8084 | Post-retrieval and grounding (not in scope yet)    |

## Versions

Java 21, Spring Boot 4.0.7, Spring Cloud 2025.1.2, Spring AI 2.0.0, Ollama.

## Run locally (without Docker)

1. Start `eureka-server` first.
2. Start the other modules in any order.
3. Open http://localhost:8761 and check that all services are listed.
4. Test routing through the gateway: `GET http://localhost:8080/ingest-static/ping`

## Progress

### Commit 1 – Architecture skeleton (`v0.1-architecture`)
- Created a Maven multi-module project with a parent `pom.xml` (Java 21, Spring Boot 4.0.7, Spring Cloud and Spring AI BOMs).
- Added 6 modules: `eureka-server`, `api-gateway`, `ingest-static`, `ingest-dynamic`, `retrieval-pre`, `retrieval-post`.
- `eureka-server`: service registry on port 8761.
- `api-gateway`: single entry point on port 8080; routes are built automatically from Eureka (e.g. `/ingest-static/**`).
- The 4 services register with Eureka and expose `/actuator/health` (needed for Docker healthchecks later).
- Each service has a temporary `/ping` endpoint to test gateway routing.
- Added `.gitignore` (excludes `target/`, `.idea/` and `.env` so no secrets are committed).

**Verification:** all services appear in the Eureka dashboard, and `GET http://localhost:8080/ingest-static/ping` returns `{"service":"ingest-static","status":"ok"}`.