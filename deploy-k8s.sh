#!/bin/bash

echo "🚀 Starting Kubernetes deployment for AI Chatbot..."

# 1. Apply secrets first so they are available for the database and app
echo "🔐 Applying secrets..."
kubectl apply -f k8s/secrets.yaml

# 2. Deploy database and cache
echo "💾 Applying MySQL deployment & service..."
kubectl apply -f k8s/mysql-deployment.yaml

echo "⚡ Applying Redis deployment & service..."
kubectl apply -f k8s/redis-deployment.yaml

# 3. Deploy the main application
echo "🤖 Applying backend deployment & service..."
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

echo "✅ Deployment complete! Run 'kubectl get pods' to check the status."
