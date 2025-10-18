#!/usr/bin/env bash
# Simple wait-for-postgres script that polls until Postgres is ready
set -euo pipefail

HOST=${DB_HOST:-${POSTGRES_HOST:-db}}
PORT=${DB_PORT:-${POSTGRES_PORT:-5432}}
USER=${POSTGRES_USER:-app}
DB=${POSTGRES_DB:-app}
TIMEOUT=${TIMEOUT:-60}

end=$((SECONDS+TIMEOUT))

until PGPASSWORD="${POSTGRES_PASSWORD:-}" pg_isready -h "$HOST" -p "$PORT" -U "$USER" -d "$DB" >/dev/null 2>&1; do
  if (( SECONDS >= end )); then
    echo "Timed out waiting for Postgres at ${HOST}:${PORT}" >&2
    exit 1
  fi
  echo "Waiting for Postgres at ${HOST}:${PORT}..."
  sleep 2
done

echo "Postgres is ready."
