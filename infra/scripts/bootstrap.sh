#!/usr/bin/env bash
set -euo pipefail

if ! command -v docker &>/dev/null; then
  echo "docker is required" >&2
  exit 1
fi

# Copy .env if missing
if [[ ! -f .env ]]; then
  cp .env.example .env
  echo "Created .env from .env.example"
fi

# Bring up the stack
export COMPOSE_PROJECT_NAME=dev-stack

docker compose up -d --build

echo "Waiting for services to become healthy..."
# Busy-wait up to 120s for api to become healthy
ATTEMPTS=0
until [ "$(docker inspect -f '{{.State.Health.Status}}' dev-api 2>/dev/null || echo starting)" = "healthy" ]; do
  ATTEMPTS=$((ATTEMPTS+1))
  if [ "$ATTEMPTS" -gt 60 ]; then
    echo "Timeout waiting for API to become healthy" >&2
    break
  fi
  sleep 2
done

API_PORT=${API_PORT:-8000}
WEB_PORT=${WEB_PORT:-3000}
ADMINER_PORT=${ADMINER_PORT:-8080}

cat <<INFO

Services are up.
- API:      http://localhost:${API_PORT}/health
- Frontend: http://localhost:${WEB_PORT}
- Adminer:  http://localhost:${ADMINER_PORT}

INFO
