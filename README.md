# 🎟️ Seatify — Event Seat Booking System

Seatify is a web-based event seat booking system built with Java and Spring Boot. It allows users to browse events, view seat availability, and book seats online. Administrators can manage events and monitor bookings through an admin panel.

The project aims to make event booking simple, organized, and convenient.

## ✨ Features

- **User Authentication** — Register, log in, and log out securely.
- **Role-Based Access** — Separate access for users and administrators.
- **Browse Events** — Explore movies, concerts, and sports events.
- **Search & Filter** — Find events by name, category, or date.
- **Seat Selection** — View seat availability and select seats.
- **Booking Management** — Confirm bookings and calculate the total price.
- **Booking History** — View previously booked tickets.
- **Admin Panel** — Manage events and view bookings.
- **Secure Passwords** — Store passwords using BCrypt hashing.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3 | Application framework |
| Spring MVC | Web request handling |
| Spring Data JPA | Database operations |
| Spring Security | Authentication and authorization |
| Hibernate | ORM |
| MySQL | Relational database |
| Thymeleaf | Server-side HTML templates |
| Bootstrap 5 | Responsive UI |
| HTML, CSS, JavaScript | Frontend |
| Maven | Dependency and build management |
| Lombok | Reduce boilerplate code |
| JUnit & Mockito | Testing |

## 📋 Prerequisites

Before running Seatify, make sure you have:

- Java Development Kit (JDK) 21
- Maven
- MySQL Server
- Git

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/kulbir3/seatify.git
cd seatify
```

### 2. Create the Database

Open MySQL and run:

```sql
CREATE DATABASE seatify;
```

### 3. Configure Database Credentials

Open `src/main/resources/application.properties` and configure your MySQL connection:

```properties
spring.application.name=seatify

spring.datasource.url=jdbc:mysql://localhost:3306/seatify
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Replace the username and password with your local MySQL credentials.

**Security tip:** Never commit real database passwords or other secrets to GitHub.

### 4. Build the Project

Run the following command from the project directory:

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

### 6. Open Seatify

Once the application starts successfully, open your browser and visit:

```text
http://localhost:8080
```

The application requires a working MySQL connection and the necessary configuration before it can start successfully.

## 📂 Project Structure

The project follows a layered Spring Boot architecture.

```text
seatify/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/seatify/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       ├── config/
│   │   │       └── SeatifyApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── images/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

*Note: This is the intended structure. Actual package and folder names may differ in the current implementation.*

## 🏗️ Application Architecture

Seatify uses a layered architecture to keep the code organized and easier to maintain.

- **Controller Layer:** Handles HTTP requests and responses.
- **Service Layer:** Contains application logic, including booking operations.
- **Repository Layer:** Communicates with the database using Spring Data JPA.
- **Entity Layer:** Defines the application's database entities.
- **Configuration Layer:** Configures security and other application settings.
- **View Layer:** Uses Thymeleaf templates to render web pages.

## 🔐 Security

Seatify uses Spring Security to manage authentication and access control.

- Passwords are hashed using BCrypt.
- User and administrator roles control access to protected features.
- Booking operations should validate seat availability before confirmation.
- Database credentials should remain outside public source code.

## 🧪 Testing

Run the project's automated tests using:

```bash
mvn test
```

Tests can cover authentication, event management, seat availability, and booking operations.

## 🔮 Future Improvements

Possible future enhancements include:

- Online payment integration
- Email booking confirmations
- Digital tickets with QR codes
- Improved seat selection interface
- Booking cancellation functionality
- Event ratings and reviews

## 🎓 Project Information

**Project Name:** Seatify  
**Project Type:** Web-Based Event Seat Booking System  
**Purpose:** College Academic Project  
**Backend:** Java and Spring Boot  
**Database:** MySQL  
**Frontend:** Thymeleaf, HTML, CSS, JavaScript, and Bootstrap

## 👨‍💻 Author

**Kulbir Bist**

GitHub: [@kulbir3](https://github.com/kulbir3)

## 📄 License

No license has been specified yet. All rights and permissions remain subject to the repository owner's choice of license.# My Project
