from fastapi import FastAPI

from app.api.v1 import router as api_v1_router


app = FastAPI(title="Backend API", version="0.1.0")


@app.get("/health", tags=["health"])
def health() -> dict[str, str]:
    return {"status": "ok"}


app.include_router(api_v1_router, prefix="/api/v1")
