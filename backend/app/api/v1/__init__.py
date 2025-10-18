from fastapi import APIRouter

router = APIRouter()


@router.get("/ping", tags=["health"])
def ping() -> dict[str, str]:
    return {"ping": "pong"}
