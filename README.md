# AI Coder — Spring Boot Chatbot

![Chat Interface](docs/images/chat.png)

A powerful, locally-hosted AI coding assistant built with **Spring Boot 3.5**, **Spring AI**, and **Ollama**. This application provides a modern, full-stack web interface for interacting with the `deepseek-coder` LLM. It includes secure user authentication, persistent chat history using Redis, and robust CSRF protection compatible with tunneling services like **ngrok**.

> **Note:** The UI screenshots shown in this document (`docs/images/*.png`) should be placed in the `docs/images/` folder to render correctly.

---

## 🚀 Features & UI Walkthrough

### 1. Modern Chat Interface
![Chat Interface](docs/images/chat.png)
- **Local AI Processing:** Uses Ollama and `deepseek-coder` to generate code without sending data to external cloud APIs.
- **Rich Markdown Formatting:** A clean, responsive interface built with HTML, CSS, and Vanilla JS. Features full Markdown rendering and syntax highlighting (via `highlight.js`) for generated code blocks.
- **Smart UX:** Includes a typing indicator, auto-resizing text areas, and quick-prompt chips for common coding questions.

### 2. Secure Authentication
![Login Page](docs/images/login.png)
![Signup Page](docs/images/signup.png)
- **Secure by Default:** Built on Spring Security 6 with BCrypt password hashing.
- **No Email Hassle:** Frictionless signup process gets users coding immediately.

### 3. Persistent Sessions
![Welcome Page](docs/images/welcome.png)
- **Persistent Chat History:** User sessions and chat logs are stored in Redis (24-hour TTL), meaning you can refresh or return later to pick up exactly where you left off.
- **ngrok Ready:** Fully configured to run behind an ngrok tunnel (handles `X-Forwarded-*` headers and SPA-style CSRF tokens over JSON `fetch()` requests).

---

## 🛠️ Technology Stack

| Component | Technology |
|---|---|
| **Backend Framework** | Spring Boot 3.5.11 |
| **AI Integration** | Spring AI 1.1.2 (Ollama integration) |
| **Database (Users)** | MySQL 8+ & Spring Data JPA |
| **Database (History)** | Redis (Lettuce Client) |
| **Security** | Spring Security 6 (Forms + Cookie XSRF) |
| **Frontend** | Thymeleaf, HTML5, CSS3, ES6 JavaScript |
| **LLM Engine** | Ollama (`deepseek-coder:latest`) |

---

## ⚙️ Prerequisites

Before running the application, ensure you have the following installed and running on your local machine:

1. **Java 17+**
2. **Maven** (or use the included `mvnw.cmd` wrapper)
3. **MySQL Server** (running on port 3306)
4. **Redis Server** (running on port 6379)
5. **Ollama** (running on port 11434)

### Setting up Ollama
Ensure Ollama is installed, then pull the required model:
```bash
ollama serve
ollama pull deepseek-coder:latest
```

### Setting up MySQL
Create a database named `ai_coder_db`. The application will automatically create the required `users` table on startup.
```sql
CREATE DATABASE ai_coder_db;
```
*(Ensure your MySQL root password is set to `root`, or update the `.env` file!)*

---

## 🔧 Configuration (`.env`)

The project uses a `.env` file for environment variables (used heavily if you deploy via Docker Compose, but also useful locally).

```properties
MYSQL_ROOT_PASSWORD=your_strong_password_here
MYSQL_DATABASE=ai_coder_db
OLLAMA_BASE_URL=http://localhost:11434
APP_PORT=8080
```
*Note: Update `src/main/resources/application.properties` directly if running locally via Maven.*

---

## 🏃‍♂️ How to Run Locally

1. Open a terminal in the project root: `c:\Users\Rohini C\Desktop\project\ai-chatbot`
2. Run the application using the Maven wrapper:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```
3. Open your browser and navigate to: `http://localhost:8080`
4. Click **Create Account**, sign up, and log in to start chatting!

> **Troubleshooting Port Errors:** If you see `Web server failed to start. Port 8080 was already in use`, it means a previous run is still active. Kill it using:
> `Get-NetTCPConnection -LocalPort 8080 | Select-Object -ExpandProperty OwningProcess | ForEach-Object { Stop-Process -Id $_ -Force }`

---

## 🌐 Hosting Publicly via ngrok

The application is specifically programmed to support ngrok's secure tunneling.

1. Start the Spring Boot application locally (as shown above).
2. Open a **new** terminal window.
3. Start ngrok on port 8080:
   ```powershell
   ngrok http 8080
   ```
4. Copy the `Forwarding` URL (e.g., `https://random-words.ngrok-free.app`).
5. Share this URL. Anyone can access the site from anywhere!

### 💡 ngrok Free Tier Warning Page
If using the free tier of ngrok, visiting the app for the first time will display a "browser warning" interstitial page.
- Simply click **"Visit Site"** to proceed to the application.
- Because the app uses proper Spring Security configuration (`server.forward-headers-strategy=framework` and Javascript `X-XSRF-TOKEN` headers), all authentication and chat features will work perfectly across the tunnel.

---

## 🛡️ Security Architecture

- **Passwords:** Hashed using `BCryptPasswordEncoder` before saving to MySQL. Never stored in plaintext.
- **CSRF Protection:** Uses a `SpaCsrfTokenRequestHandler`. This provides:
  - Form protection via Thymeleaf hidden inputs (`_csrf.token`).
  - Dynamic JSON POST protection via an `XSRF-TOKEN` cookie that the frontend reads and sends back as an `X-XSRF-TOKEN` header on every chat message.
- **Forwarded Headers:** Spring natively unpacks `X-Forwarded-Proto` and `X-Forwarded-Host` from ngrok to ensure redirect URLs (like login success/failure) resolve securely to `https://`.

---

## 📁 Project Structure

```text
ai-chatbot/
├── src/main/java/com/example/chatbot/
│   ├── config/          # Security & Redis configurations
│   ├── controller/      # Web Controllers (Auth, Chat)
│   ├── model/           # Entities (User, ChatMessage)
│   ├── repository/      # Spring Data JPA interfaces
│   └── service/         # Business Logic (User details, AI Chat)
├── src/main/resources/
│   ├── application.properties  # Main app config
│   └── templates/       # Thymeleaf HTML files (index, login, signup)
├── .env                 # Environment variables
├── pom.xml              # Maven dependencies
└── docker-compose.yml   # Optional Docker execution
```
