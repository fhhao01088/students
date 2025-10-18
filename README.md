# Monorepo Scaffold: FastAPI + Next.js 14

This repository is a starting point for a full‑stack monorepo containing:

- backend: FastAPI service (Python) with Poetry
- frontend: Next.js 14 (TypeScript) with Tailwind CSS
- infra: Infrastructure as code (placeholder)
- .github: GitHub-related docs/templates (placeholder)

Shared developer tooling is provided via:
- .editorconfig for consistent editor behavior
- .gitignore for Python and Node.js artifacts
- .pre-commit-config.yaml to enforce formatting and linting on commit (Python + general hooks)

## Layout

- backend/: FastAPI app and Python tooling
- frontend/: Next.js app with ESLint + Prettier + Tailwind
- infra/: Placeholder for infrastructure code
- .github/: Placeholder for GitHub templates/config

## Quickstart

### Prerequisites
- Python 3.11+
- Poetry (recommended) or a Python virtual environment
- Node.js 18.17+ and npm (or pnpm/yarn)

### Backend (FastAPI)

Install dependencies and run the API locally:

```bash
cd backend
poetry install --no-root
poetry run uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

Formatting, linting, and type checking:

```bash
# From backend/
make format      # ruff --fix, isort, black
make lint        # ruff, isort --check, black --check
make typecheck   # mypy
```

Install pre-commit hooks (run once):

```bash
# From backend/
poetry run pre-commit install
```

### Frontend (Next.js 14 + Tailwind)

Install dependencies and run the dev server:

```bash
cd frontend
npm install
npm run dev
```

Formatting, linting, and type checking:

```bash
# From frontend/
npm run format   # Prettier
npm run lint     # ESLint (Next.js rules + Prettier)
npm run typecheck
```

## Project Goals

- Provide a clean monorepo scaffold with consistent tooling across backend and frontend.
- Enable fast local development with strong defaults for formatting, linting, and static typing.
- Prepare a simple baseline for CI without enforcing any workflow here.

## Notes

- Pre-commit hooks are configured to operate primarily on the backend Python code and general file hygiene. Frontend formatting/linting is available via npm scripts.
- Add infrastructure (Terraform, Pulumi, CDK, etc.) contents under infra/ as needed.
