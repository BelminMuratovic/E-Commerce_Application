.PHONY: up down logs help

help:
	@echo "Available commands:"
	@echo "  make up    - Start the project (setup and run)"
	@echo "  make down  - Stop and clean up the project"
	@echo "  make logs  - Follow application logs"
	@echo "  make help  - Show this help message"

up:
	@echo "Starting project..."
	@./scripts/setup.sh

down:
	@echo "Stopping project..."
	@./scripts/cleanup.sh

logs:
	@tail -f ./logs/application.log