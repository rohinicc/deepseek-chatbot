# 🤖 AI Coder — Local AI Coding Assistant

A locally-running AI coding assistant built with **Spring Boot** and **Ollama**.  
Ask coding questions, get code snippets, debug errors — all running privately on your machine.

---

## 📸 Screenshots

### 🏠 Welcome
<img width="2878" height="1627" alt="Welcome Page" src="https://github.com/user-attachments/assets/f852cbae-0f4c-48ba-b143-84e8ea4649ed" />

### 💬 Chat
<img width="2879" height="1547" alt="Chat Page" src="https://github.com/user-attachments/assets/fec735f0-de0a-4235-accc-f88f1651af13" />

---

## ✨ Features

- 🔐 User authentication — signup, login, logout
- 💬 Real-time chat with local AI
- ⌨️ Typing indicator while AI is thinking
- 📝 Markdown rendering — code blocks, bold, lists
- 🕘 Persistent chat history with auto-expiry
- 🧹 Clear chat button
- 💡 Suggestion chips to get started quickly
- 🔒 100% local — no data leaves your machine

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.x |
| AI | Spring AI + Ollama |
| Security | Spring Security |
| Database | MySQL + Redis |
| Frontend | Thymeleaf, HTML, CSS, JS |
| Build | Maven |

---

## ✅ Requirements

| Requirement | Version |
|---|---|
| Java JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 8.x |
| Redis | Latest |
| Ollama | Latest |

---

## 🚀 Run Locally

```bash
# 1. Pull the AI model
ollama pull deepseek-coder
ollama serve

# 2. Clone the project
git clone https://github.com/rohinicc/ai-chatbot-production.git
cd ai-chatbot-production

# 3. Configure
cp src/main/resources/application.properties.example \
   src/main/resources/application.properties
# fill in your MySQL and Redis values

# 4. Run
mvn spring-boot:run
```

Open `http://localhost:8080` → create account → start chatting 🎉

---

## 🐳 Run with Docker

```bash
# create .env with your values
docker-compose up -d --build
```

Open `http://localhost:8080`

---

## ⚠️ Troubleshooting

| Problem | Fix |
|---|---|
| Site can't be reached | Verify app is running on port 8080 |
| AI not responding | Run `ollama serve` and check `ollama list` |
| BUILD FAILURE | Verify Java 17 — `java -version` |
| Database error | Check credentials in `application.properties` |

---

## 👩‍💻 Developer

**Rohini C**  
[GitHub](https://github.com/rohinicc) · [LinkedIn](https://linkedin.com/in/rohini-c-na16/) · [Portfolio](https://rohinijuji.onrender.com)
