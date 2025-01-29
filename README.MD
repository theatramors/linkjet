# LinkJet

A simple self-hosted url shortener with embedded H2 database.

## Docker

Docker-compose file:

```yaml
services:
  linkjet:
    image: "ghcr.io/theatramors/linkjet:latest"
    restart: always
    volumes:
      - /your/path:/app/data
    ports:
      - "8080:8080"
    environment:
      SERVER_PORT: 8080
      LINK_LENGTH: 8
```
