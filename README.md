
A beginner-friendly, industry-aligned voting application built with **Spring Boot**, **Spring Security**, and **MySQL**. This project demonstrates secure authentication, role-based access control, and a clean layered architecture.

## Features

- Role-based access (ADMIN and USER)
- Secure authentication using Spring Security
- RESTful APIs for election management and voting
- MySQL database (H2 for local testing)
- Clean Controller → Service → Repository architecture
- Prevention of duplicate voting

## Technologies Used

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL (or H2 for local testing)
- Maven

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/secure-voting-springboot.git
   ```
2. Navigate to the project directory:
   ```bash
   cd secure-voting-springboot
   ```
3. Update database configuration in `src/main/resources/application.properties` (for MySQL) or use H2 for quick testing.
4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the API at `http://localhost:8080`.

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/register-admin` - Register a new admin
- `POST /api/auth/login` - Login (creates a session)

### Admin APIs

- `POST /api/admin/elections` - Create an election
- `POST /api/admin/elections/candidates` - Add a candidate to an election
- `GET /api/admin/elections/{electionId}/results` - View election results

### User APIs

- `GET /api/user/elections/active` - View active elections
- `POST /api/user/elections/vote` - Cast a vote

## Folder Structure

```
src/main/java/com/example/securevoting/
├── SecureVotingApplication.java
├── config/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── exception/
└── resources/
```

## Security

- Passwords are hashed using BCrypt.
- Endpoints are secured using Spring Security.
- Duplicate voting is prevented at both the service and database level.

