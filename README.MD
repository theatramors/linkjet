# LinkJet

A simple self-hosted url shortener with embedded H2 database.

## Docker
Docker-compose file:
```yaml
services:
  linkjet:
    image: "ghcr.io/theatramors/linkjet:latest"
    restart: always
    ports:
      - "8080:8080"
```
