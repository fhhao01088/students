import os
import socket
from typing import Dict

from fastapi import FastAPI
import psycopg2
from psycopg2 import OperationalError

app = FastAPI(title="Example API", version="0.1.0")


def _get_db_conn_params():
    # Prefer DATABASE_URL if provided, otherwise build from individual vars
    database_url = os.getenv("DATABASE_URL")
    if database_url:
        return {"dsn": database_url}
    return {
        "dbname": os.getenv("POSTGRES_DB", "app"),
        "user": os.getenv("POSTGRES_USER", "app"),
        "password": os.getenv("POSTGRES_PASSWORD", "app"),
        "host": os.getenv("DB_HOST", os.getenv("POSTGRES_HOST", "db")),
        "port": int(os.getenv("DB_PORT", os.getenv("POSTGRES_PORT", "5432"))),
    }


def db_is_healthy() -> bool:
    params = _get_db_conn_params()

    try:
        if "dsn" in params:
            conn = psycopg2.connect(params["dsn"], connect_timeout=1)
        else:
            conn = psycopg2.connect(connect_timeout=1, **params)
        conn.close()
        return True
    except OperationalError:
        return False


@app.get("/health")
def health() -> Dict[str, str]:
    # DB health is informative only; API health should be true even if DB is starting up
    db_status = "up" if db_is_healthy() else "starting"
    return {
        "status": "ok",
        "service": "api",
        "hostname": socket.gethostname(),
        "db": db_status,
    }


@app.get("/")
def root() -> Dict[str, str]:
    return {"message": "API is running. See /health"}
