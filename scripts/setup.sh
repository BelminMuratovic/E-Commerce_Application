#!/bin/bash
echo "Setting up E-Commerce Application"

# Build images
echo "Building docker images"
docker compose build

# Start services
echo "Starting services"
docker compose up -d --wait

# Show status
echo "Service status"
docker compose ps

echo "Frontend: http://localhost:4200"
echo "Backend API: http://localhost:8080"