.DEFAULT_GOAL := help

.PHONY: help
help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .+' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-22s\033[0m %s\n", $$1, $$2}'

.PHONY: install
install: ## Install dependencies with Poetry
	poetry install --no-root

.PHONY: run
run: ## Run the FastAPI app with Uvicorn
	poetry run uvicorn app.main:app --reload --host 0.0.0.0 --port 8000

.PHONY: format
format: ## Format code (ruff --fix, isort, black)
	poetry run ruff --fix .
	poetry run isort .
	poetry run black .

.PHONY: lint
lint: ## Lint code (ruff, isort --check-only, black --check)
	poetry run ruff .
	poetry run isort --check-only .
	poetry run black --check .

.PHONY: typecheck
typecheck: ## Type check with mypy
	poetry run mypy .

.PHONY: pre-commit-install
pre-commit-install: ## Install pre-commit hooks
	poetry run pre-commit install
