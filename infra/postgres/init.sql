-- Example init script to seed database on first run
-- This runs automatically on first container startup via docker-entrypoint-initdb.d
CREATE TABLE IF NOT EXISTS healthcheck (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    note TEXT
);
