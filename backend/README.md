# Backend (FastAPI)

FastAPI application scaffolded with Poetry and development tooling:

- Formatter: black
- Linter: ruff (+ isort for import sorting)
- Type checker: mypy
- Pre-commit hooks: configured at repository root

## Requirements

- Python 3.11+
- Poetry (https://python-poetry.org/)

## Setup

```bash
poetry install --no-root
```

## Running locally

```bash
poetry run uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

- Health endpoint: http://localhost:8000/health
- API v1 example: http://localhost:8000/api/v1/ping

## Developer tooling

```bash
# Format (ruff --fix, isort, black)
make format

# Lint (ruff, isort --check-only, black --check)
make lint

# Type check
make typecheck

# Install pre-commit hooks
make pre-commit-install
```

## Project structure

```
backend/
  app/
    api/
      v1/
        __init__.py
    __init__.py
    main.py
  pyproject.toml
  mypy.ini
  Makefile
```
