# 🤖 AI Coder Chatbot

A local AI-powered coding assistant built with **Spring Boot** and **Ollama**.  
Ask coding questions, get code snippets, debug errors — all running privately on your machine.

---

## 📸 Live Project Screenshots

### 🏠 Welcome Page
> Landing page when you open the app — click **Start Chatting** to begin.

<img width="2878" height="1627" alt="Screenshot 2026-03-11 233142" src="https://github.com/user-attachments/assets/f852cbae-0f4c-48ba-b143-84e8ea4649ed" />

---

### 💬 Chat Page
> Main chat interface — type your coding question and get an instant AI response.

<img width="2879" height="1547" alt="Screenshot 2026-03-12 075150" src="https://github.com/user-attachments/assets/fec735f0-de0a-4235-accc-f88f1651af13" />

---

## ✨ Features

- 💬 Real-time chat with AI
- ⌨️ Typing indicator while AI is thinking
- 📝 Markdown rendering (code blocks, bold, lists)
- 🕘 Session-based conversation history
- 🧹 Clear chat button
- 💡 Suggestion chips to get started quickly
- 🔒 100% local — no data sent to the internet

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.x |
| AI Integration | Spring AI 1.1.2 |
| Local LLM Runner | Ollama |
| Frontend | Thymeleaf, HTML, CSS, JavaScript |
| Build Tool | Maven |

---

## ✅ Requirements

Make sure you have the following installed before running the project:

| Requirement | Version | Download |
|---|---|---|
| Java JDK | 17 or above | [Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) |
| Maven | 3.6 or above | [Download](https://maven.apache.org/download.cgi) |
| Ollama | Latest | [Download](https://ollama.com) |
| Git | Latest | [Download](https://git-scm.com) |

---

## 🚀 How to Run the Project

### 1. Install Ollama

Download and install Ollama from [https://ollama.com](https://ollama.com)

Verify installation:

```bash
ollama --version
```

---

### 2. Pull an AI Model

```bash
ollama pull deepseek-coder
```

> ⏳ Wait until you see `success`

Verify the model is available:

```bash
ollama list
```

---

### 3. Clone the Project

```bash
git clone https://github.com/rohinicc/deepseek-chatbot.git
cd deepseek-chatbot
```

---

### 4. Build the Project

```bash
mvn clean package -DskipTests
```

Wait for:

```
BUILD SUCCESS
```

---

### 5. Run the App

```bash
mvn spring-boot:run
```

If the server starts successfully, you should see:

```
Started ChatbotApplication in X seconds
```

---

### 6. Open in Browser

```
http://localhost:8080
```

You will see the **Welcome Page** → click **Start Chatting** → start asking! 🎉

---

## 🔄 Application Flow

```
User Request
      │
      ▼
Spring Boot Application (Port 8080)
      │
      ▼
ChatService → Spring AI
      │
      ▼
Ollama API (Port 11434)
      │
      ▼
AI Model (Local)
      │
      ▼
Response rendered in Browser
```

---

## 🔗 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/` | Redirects to welcome page |
| GET | `/welcome` | Welcome landing page |
| GET | `/chat-page` | Main chat UI |
| POST | `/chat` | Send a message, get AI response |
| POST | `/clear` | Clear chat history |

---

## ⚠️ Troubleshooting

| Problem | Fix |
|---|---|
| `Site can't be reached` | Make sure app is running on port 8080 |
| `Could not reach Ollama` | Run `ollama serve` to start Ollama |
| `BUILD FAILURE` | Verify Java 17 is installed — `java -version` |
| Model not responding | Run `ollama list` to verify model is downloaded |

---

## 📝 Notes

- Ollama must be installed and running for AI responses to work.
- Make sure port `8080` is open if accessing from another machine.
- Keep Ollama running in the background while using the app.

---

## 🙏 Acknowledgement

Special thanks to **Akshay Kumar S** for the invaluable mentorship, constant support, and guidance throughout this project. 🌟

---

## 👩‍💻 Developer

**Rohini C**  
📎 [GitHub](https://github.com/rohinicc) · [LinkedIn](https://linkedin.com/in/rohini-c-na16/) · [Portfolio](https://rohinijuji.onrender.com)
