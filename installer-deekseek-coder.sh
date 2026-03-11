#!/bin/bash

# ─────────────────────────────────────────────
#  DeepSeek Coder + Ollama - Remote Server Setup
#  Run as: bash setup.sh
# ─────────────────────────────────────────────

set -e  # Exit on any error

echo "================================================"
echo "  DeepSeek Coder - Ollama Remote Server Setup  "
echo "================================================"

# ── Step 1: Update system ─────────────────────
echo ""
echo "[1/7] Updating system..."
sudo apt update -y && sudo apt upgrade -y

# ── Step 2: Install dependencies ─────────────
echo ""
echo "[2/7] Installing curl and dependencies..."
sudo apt install -y curl wget git

# ── Step 3: Install Ollama ────────────────────
echo ""
echo "[3/7] Installing Ollama..."
curl -fsSL https://ollama.com/install.sh | sh

# ── Step 4: Configure Ollama for remote access
echo ""
echo "[4/7] Configuring Ollama for remote access..."
sudo mkdir -p /etc/systemd/system/ollama.service.d

sudo bash -c 'cat <<EOF > /etc/systemd/system/ollama.service.d/override.conf
[Service]
Environment="OLLAMA_HOST=0.0.0.0:11434"
EOF'

# ── Step 5: Reload and restart Ollama service
echo ""
echo "[5/7] Reloading and restarting Ollama service..."
sudo systemctl daemon-reexec
sudo systemctl daemon-reload
sudo systemctl enable ollama
sudo systemctl restart ollama

echo "Waiting for Ollama to start..."
sleep 5

# ── Step 6: Pull DeepSeek Coder model ────────
echo ""
echo "[6/7] Pulling DeepSeek Coder model..."
ollama pull deepseek-coder

# ── Step 7: Verify installation ──────────────
echo ""
echo "[7/7] Verifying installation..."
systemctl status ollama --no-pager

# ── Get server IP ─────────────────────────────
SERVER_IP=$(hostname -I | awk '{print $1}')

echo ""
echo "================================================"
echo "  Installation Complete!                        "
echo "================================================"
echo ""
echo "  Ollama is running on:"
