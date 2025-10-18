#!/usr/bin/env bash
set -euo pipefail

# Initialize or migrate database schema after services are up
# Requires: docker compose, and environment variables sourced from .env

if ! command -v docker &>/dev/null; then
  echo "docker is required" >&2
  exit 1
fi

compose_cmd=(docker compose)

DB_SERVICE=${DB_SERVICE:-db}
POSTGRES_USER=${POSTGRES_USER:-app}
POSTGRES_DB=${POSTGRES_DB:-app}

SQL=${1:-}
if [[ -z "${SQL}" ]]; then
  # default operation: ensure example table exists
  SQL='CREATE TABLE IF NOT EXISTS example (id SERIAL PRIMARY KEY, created_at TIMESTAMPTZ NOT NULL DEFAULT NOW());'
fi

${compose_cmd[@]} exec -T "$DB_SERVICE" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -v ON_ERROR_STOP=1 -c "$SQL"

echo "Database init/migration completed."
