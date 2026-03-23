
#!/bin/bash

echo "Updating system..."
sudo apt update -y

echo "Installing curl..."
sudo apt install -y curl

echo "Installing Ollama..."
curl -fsSL https://ollama.com/install.sh | sh

echo "Creating Ollama service override for remote access..."

sudo mkdir -p /etc/systemd/system/ollama.service.d

sudo bash -c 'cat <<EOF > /etc/systemd/system/ollama.service.d/override.conf
[Service]
Environment="OLLAMA_HOST=0.0.0.0:11434"
EOF'

echo "Reloading systemd..."
sudo systemctl daemon-reexec
sudo systemctl daemon-reload

echo "Restarting Ollama service..."
sudo systemctl restart ollama

echo "Waiting for Ollama to start..."
sleep 5

echo "Pulling DeepSeek Coder model..."
ollama pull deepseek-coder

echo "Checking Ollama status..."
systemctl status ollama --no-pager

echo "Installation complete!"

echo "Test with:"
echo "curl http://localhost:11434"
