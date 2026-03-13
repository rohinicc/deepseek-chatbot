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

## 🧠 How It Works

```
You type a question
        ↓
Spring Boot receives it (ChatController)
        ↓
ChatService sends it to Ollama
        ↓
Ollama runs the AI model locally on your machine
        ↓
Response comes back to your browser
        ↓
Markdown is rendered (code blocks, bold, lists)
```

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

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/chatbot/
│   │   ├── ChatbotApplication.java      ← Entry point
│   │   ├── controller/
│   │   │   └── ChatController.java      ← Handles routes & requests
│   │   ├── service/
│   │   │   └── ChatService.java         ← Talks to Ollama via Spring AI
│   │   └── model/
│   │       └── ChatMessage.java         ← Message model (role, content, time)
│   └── resources/
│       └── templates/
│           ├── welcome.html             ← Landing page
│           └── index.html               ← Chat UI
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- [Ollama](https://ollama.com) installed and running

### Step 1 — Install Ollama

Download from [https://ollama.com](https://ollama.com) and install it.

### Step 2 — Pull a Model

```bash
ollama pull <your-model-name>
```

### Step 3 — Clone the Project

```bash
git clone https://github.com/rohinicc/deepseek-chatbot.git
cd deepseek-chatbot
```

### Step 4 — Run the App

```bash
mvn spring-boot:run
```

### Step 5 — Open in Browser

```
http://localhost:8080
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

## ✨ Features

- 💬 Real-time chat with AI
- ⌨️ Typing indicator while AI is thinking
- 📝 Markdown rendering (code blocks, bold, lists)
- 🕘 Session-based conversation history
- 🧹 Clear chat button
- 💡 Suggestion chips to get started quickly
- 🔒 100% local — no data sent to the internet

---

## 🙏 Acknowledgement

Special thanks to **Akshay Kumar S** for the invaluable mentorship, constant support, and guidance throughout this project. 🌟

---

## 👩‍💻 Developer

**Rohini C** — Java Developer & DevOps Engineer  
📎 [GitHub](https://github.com/rohinicc) · [LinkedIn](https://linkedin.com/in/rohini-c-na16/) · [Portfolio](https://rohinijuji.onrender.com)
