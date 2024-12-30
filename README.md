# library-Management-System

## Overview

This project is a library management system developed using Spring Framework. It integrates JPA for database interaction, PostgreSQL as the database, and ActiveMQ for messaging. The project consists of two modules: Library-service and User-service.

## Prerequisites

Before compiling and running the program, ensure you have the following installed:

- Java Development Kit (JDK) 11 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- ActiveMQ 5.15 or higher
- Any Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse (optional)

## Setup Instructions

### 1. Clone the Repository

Clone the project repository from https://github.com/chrisogwuche/library-Management-System.git:

### 2. Configure the Database

1. Install PostgreSQL for the project.

2. Update the database configuration in the `application.properties` or `application.yml` file:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/project_db
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

### 3. Configure ActiveMQ

1. Install ActiveMQ and start the server.
2. Update the ActiveMQ configuration in the `application.properties` file:
   ```properties
   spring.activemq.broker-url=tcp://localhost:61616
   spring.activemq.user=<your-username>
   spring.activemq.password=<your-password>
   ```

### 4. Build the Project

Use Maven to build the project:

```bash
mvn clean install
```

### 5. Run the Application

library-service

Navigate to the library-service module directory and run the application:

```bash
cd library-service
mvn spring-boot:run
```
User-service

Navigate to the user-service module directory and run the application:

```bash
cd user-service
mvn spring-boot:run
```

Alternatively, if you are using an IDE, run the `main` method in the `Application` class.

### 6. Verify the Application

- Access the respective services at their configured ports (default is http://localhost:8080 for library-service and http://localhost:8081 for user-service).
- Ensure ActiveMQ and PostgreSQL services are running correctly.

## Additional Libraries and Frameworks

The following libraries and frameworks are used in this project:

- **Spring Framework**: For dependency injection, MVC, and other utilities.
- **Spring Boot**: For simplifying the setup and running of the application.
- **Spring Data JPA**: For interacting with the database.
- **PostgreSQL**: As the database.
- **ActiveMQ**: For messaging and event-driven communication.

## Troubleshooting

### Common Issues

1. **Database Connection Errors**:

    - Ensure PostgreSQL is running and the credentials in `application.properties` are correct.

2. **ActiveMQ Connection Errors**:

    - Verify that ActiveMQ is running on `localhost:61616`.

3. **Port Conflicts**:

    - If `8080` is already in use, change the port in `application.properties`:
      ```properties
      server.port=<new-port>
      ```
