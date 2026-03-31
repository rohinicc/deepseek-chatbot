#!/bin/bash

# Ensure we stop execution if any command fails
set -e

# Define variables
IMAGE_NAME="rohinicc/ai-chatbot"
TAG="latest"

echo "=========================================="
echo "🐳 Preparing to Build and Push Docker Image"
echo "=========================================="

echo "🔨 [1/2] Building Docker image: ${IMAGE_NAME}:${TAG}..."
docker build -t ${IMAGE_NAME}:${TAG} .

echo "☁️ [2/2] Pushing Docker image to Docker Hub..."
docker push ${IMAGE_NAME}:${TAG}

echo "✅ Success! Docker image successfully pushed to Docker Hub."
echo "You can now pull the image anywhere using: docker pull ${IMAGE_NAME}:${TAG}"
