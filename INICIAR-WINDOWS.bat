@echo off
cd /d "%~dp0"
where docker >nul 2>nul
if errorlevel 1 (
  echo Docker nao encontrado. Instale e inicie o Docker Desktop.
  pause
  exit /b 1
)
docker compose up --build
pause
