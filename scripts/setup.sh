#!/bin/bash
echo "Setting up E-Commerce Application"

# Check if .env file exists
cd "$(dirname "$0")/.." || exit 1
if [ ! -f .env ]; then
    echo ".env file not found"
    echo "Creating .env from .env.example"

    if [ -f .env.example ]; then
        cp .env.example .env
        echo ".env file created"
    else
        echo ".env.example not found"
        exit 1
    fi
fi

# Export .env variables
set -o allexport
source .env
set +o allexport

# Build images
echo "Building docker images"
docker compose build

# Start services
echo "Starting services"
docker compose up -d --wait

# Show status
echo "Service status"
docker compose ps

echo "Frontend: http://localhost:${FRONTEND_PORT}"
echo "Backend API: http://localhost:${BACKEND_PORT}"