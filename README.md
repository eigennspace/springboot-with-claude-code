# Spring Boot Demo Application with GLM-4.5

Demo Project Spring Boot dengan Claude Code CLI sebagai AI assistant.

(Bahkan documentation ini dibuat oleh Ai dengan sedikit modifikasi)

## 🚀 Teknologi

- **Spring Boot 3.5.6** - Framework aplikasi Java
- **Java 17** - Bahasa pemrograman
- **Maven** - Build automation
- **H2 Database** - Database in-memory
- **Spring Data JPA** - ORM database
- **Spring Validation** - Validasi input
- **Spring Actuator** - Monitoring aplikasi
- **Lombok** - Reduksi boilerplate code
- **Claude Code CLI** - AI assistant untuk development

## 📋 Prasyarat

- Java 17+
- Maven 3.6+
- Claude Code CLI terinstall (silahkan lihat tutor di internet bagaimana cara instal claude code CLI)
- Z.AI API key (untuk akses ke GLM-4.5 menggunakan Claude Code)

## 🔧 Setup Project

### 1. Konfigurasi Environment Variables

Buat file `.env` di root project berdasarkan `.env-sample`

### 2. Install Dependencies

```bash
mvn clean install
```

## 🚀 Menjalankan Aplikasi

### 1. Start Spring Boot Application

```bash
mvn spring-boot:run
```

### 2. Menggunakan Claude Code CLI

Start Claude Code dengan script yang disediakan:

```bash
./start-claude.sh
```

Script ini akan:
- Load environment variables dari file `.env`
- Start Claude Code CLI dengan Z.AI sebagai code asistant

### 3. Tracking Usage

Track penggunaan prompt Claude Code:

```bash
./usage-claude.sh
```

Script ini akan:
- Scan semua project di `~/.claude/projects`
- Hitung jumlah user prompts yang dikirim ke Z.AI
- Simpan hasil di `prompt-usage/data.txt`

## 📁 Struktur Project

```
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java
│   │   │   └── ... (package structure)
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/demo/
└── pom.xml
```

## 🔌 Claude Code CLI dengan Z.AI

### Claude Code Configuration

1. **start-claude.sh**: Script untuk memulai Claude Code CLI yang terhubung ke Z.AI backend
2. **usage-claude.sh**: Script untuk tracking penggunaan prompt ke Z.AI

### Cara Kerja Claude Code x Z.AI

- Claude Code CLI berjalan sebagai AI assistant di terminal
- Setiap prompt/user request dikirim ke Z.AI backend
- Z.AI memproses dan memberikan response coding assistance
- Semua interaksi tersimpan di `~/.claude/projects/`

### Workflow Development

1. **Start Claude Code**: Jalankan `./start-claude.sh`
2. **Prompting**: Berikan instruksi coding ke Claude Code
3. **AI Assistance**: Z.AI memproses dan memberikan code suggestions
4. **Implementation**: Terapkan code yang dihasilkan AI
5. **Testing**: Jalankan tests dengan Maven
6. **Track Usage**: Monitor penggunaan prompt dengan `./usage-claude.sh`

## 🧪 Testing

Jalankan semua tests:

```bash
mvn test
```

Test coverage report:

```bash
mvn jacoco:report
```

## 📊 Monitoring

Gunakan Spring Actuator endpoints:

- Health check: `GET /actuator/health`
- Application info: `GET /actuator/info`
- Metrics: `GET /actuator/metrics`

## 🛠️ Development Commands

```bash
# Build project
mvn clean compile

# Run tests
mvn test

# Run application
mvn spring-boot:run

# Package application
mvn clean package

# Run with custom profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 📝 Kontribusi

1. Fork repository
2. Create feature branch
3. Make changes with Claude Code assist
4. Commit changes
5. Push to branch
6. Create Pull Request

## 📄 License

This project is licensed under the MIT License.