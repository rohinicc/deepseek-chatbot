@echo off
echo 🚀 Starting Kubernetes deployment for AI Chatbot...

echo 🔐 Applying secrets...
kubectl apply -f k8s\secrets.yaml

echo 💾 Applying MySQL deployment & service...
kubectl apply -f k8s\mysql-deployment.yaml

echo ⚡ Applying Redis deployment & service...
kubectl apply -f k8s\redis-deployment.yaml

echo 🤖 Applying backend deployment & service...
kubectl apply -f k8s\deployment.yaml
kubectl apply -f k8s\service.yaml

echo ✅ Deployment complete! Run 'kubectl get pods' to check the status.
pause
