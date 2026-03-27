# AI Coder

A locally-running AI coding assistant built with **Spring Boot** and **Ollama**.  
Ask coding questions, get instant AI responses — running privately on your machine.

---

## Screenshots

### Welcome
<img width="2878" alt="Welcome Page" src="https://github.com/user-attachments/assets/f852cbae-0f4c-48ba-b143-84e8ea4649ed" />

### Sign Up
<img width="2879" alt="Signup Page" src="https://github.com/user-attachments/assets/fec735f0-de0a-4235-accc-f88f1651af13" />

### Login
<img width="2879" alt="Login Page" src="https://github.com/user-attachments/assets/fec735f0-de0a-4235-accc-f88f1651af13" />

### Chat
<img width="2879" alt="Chat Page" src="https://github.com/user-attachments/assets/fec735f0-de0a-4235-accc-f88f1651af13" />

---

## Features

- 🔐 User authentication — signup, login, logout
- 💬 Real-time chat with local AI model
- ⌨️ Typing indicator while AI is thinking
- 📝 Markdown rendering — code blocks, bold, lists
- 🕘 Chat history panel with today's questions
- 🧹 Clear chat button
- 🔒 100% local — no data leaves your machine
- 🐳 Docker ready — one command deployment

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.x |
| AI | Spring AI + Ollama |
| Model | deepseek-coder:1.3b |
| Security | Spring Security |
| Database | MySQL 8 |
| Cache | Redis |
| Frontend | Thymeleaf, HTML, CSS, JS |
| Deployment | Docker Compose, AWS EC2 |

---

## Requirements

| Tool | Version |
|---|---|
| Java JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 8.x |
| Redis | Latest |
| Ollama | Latest |
| Docker | Latest (for deployment) |

---

## Run Locally

```bash
# 1. Pull AI model
ollama pull deepseek-coder:1.3b
ollama serve

# 2. Clone
git clone https://github.com/rohinicc/ai-chatbot.git
cd ai-chatbot

# 3. Configure
cp src/main/resources/application.properties.example \
   src/main/resources/application.properties
# fill in your MySQL and Redis values

# 4. Run
mvn spring-boot:run
```

Open `http://localhost:8080`

---

## Run with Docker (AWS / Server)

```bash
# 1. Create .env file
cp .env.example .env
# fill in your values

# 2. Start everything
docker-compose up -d --build
```

Open `http://YOUR_SERVER_IP:8080`

---

## Project Structure

```
src/
├── main/
│   ├── java/com/example/chatbot/
│   │   ├── config/        # Security, Redis config
│   │   ├── controller/    # Auth, Chat controllers
│   │   ├── model/         # User, ChatMessage
│   │   ├── repository/    # User repository
│   │   └── service/       # Auth, Chat, History services
│   └── resources/
│       └── templates/     # Thymeleaf HTML pages
Dockerfile
docker-compose.yml
```

---

## Troubleshooting

| Problem | Fix |
|---|---|
| Site can't be reached | Check app is running on port 8080 |
| AI not responding | Run `ollama serve` and check `ollama list` |
| Database error | Check credentials in `application.properties` |
| Docker MySQL failing | Check `.env` has real password, not placeholder |
| BUILD FAILURE | Verify Java 17 — `java -version` |

---

## Developer

**Rohini C**  
[GitHub](https://github.com/rohinicc) · [LinkedIn](https://linkedin.com/in/rohini-c-na16/) · [Portfolio](https://rohinijuji.onrender.com)
