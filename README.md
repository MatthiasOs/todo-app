# 📝 TODO App (Spring Boot + H2)

A simple **REST-based TODO list application** built with Spring Boot, storing tasks in an **in-memory H2 database**.  
Perfect for practicing HTTP requests (GET, POST, PUT, DELETE) and integration testing with Spring Boot & AssertJ.

---

## 🚀 Features

- CRUD operations for TODO items:
    - `GET /api/todos` → Retrieve all Todos
    - `GET /api/todos/completed` → Retrieve all completed Todos
    - `POST /api/todos` → Create a new Todo
    - `PUT /api/todos/{id}` → Update a Todo
    - `DELETE /api/todos/{id}` → Delete a Todo
- In-memory database (**H2**)
- Web console for H2 access
- Unit and integration tests (JUnit 5 + AssertJ)
- Testable via **Bruno** or **Postman**

---

## 🧱 Project Structure

```
todo-app/
 ├─ src/
 │  ├─ main/
 │  │  ├─ java/de/ossi/todo/
 │  │  │  ├─ controller/   → REST Controller
 │  │  │  ├─ service/      → Business logic
 │  │  │  ├─ model/        → Entity classes (e.g. Todo)
 │  │  │  └─ repository/   → JPA Repository
 │  │  └─ resources/
 │  │     └─ application.properties
 │  └─ test/
 │     ├─ java/de/ossi/todo/
 │     │  ├─ controller/   → Unit test
 │     │  └─ ApplicationTest
 │     └─ resources/
 │        └─ ToDoAppHttp -> Bruno Http Test Requests
 └─ pom.xml
```

---

## ⚙️ Requirements

- Java 25 (or compatible version)
- Maven 3.9+
- IntelliJ IDEA or another IDE with Spring support

---

## ▶️ Running the Application

### **Option 1: From IntelliJ**

1. Open the project as a Maven project.
2. Run the class:
   ```
   de.ossi.todo.TodoAppApplication
   ```
3. The app runs on:  
   👉 [http://localhost:9090](http://localhost:9090)

---

### **Option 2: From the command line**

```bash
mvn clean package
mvn spring-boot:run
```

---

## 🗄️ H2 Database

**Configuration (application.properties):**

```properties
spring.application.name=todo-app
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.jpa.show-sql=true
server.port=9090
```

**H2 Console:**
👉 [http://localhost:9090/h2-console](http://localhost:9090/h2-console)

JDBC URL:

```
jdbc:h2:mem:testdb
```

---

## 🧪 Tests

### Unit Tests (fast, no Spring context)

```bash
mvn test
```

### TodoAppApplicationTest (with full Spring Boot context & DB)

- Tests the full request–response lifecycle
- Database is reset for each test run

---

## 🌐 Example Requests (Bruno or Postman)

### GET all Todos

```
GET http://localhost:9090/api/todos
```

### POST create a new Todo

```json
POST http://localhost:9090/api/todos
Content-Type: application/json

{
"title": "Buy milk",
"description": "Buy milk description",
"completed": false
}
```

### PUT update a Todo

```json
PUT http://localhost:9090/api/todos/1
Content-Type: application/json

{
"title": "Buy milk and bread",
"description": "Buy milk description",
"completed": true
}
```

### DELETE a Todo

```
DELETE http://localhost:9090/api/todos/1
```

---

## 📋 TODOs / Next Steps

- [ ] Add validation for request data using `@Valid`
- [ ] Implement exception handling (`@ControllerAdvice`)
- [ ] Add Swagger / OpenAPI documentation
- [ ] Switch from H2 to a persistent database (PostgreSQL or MySQL)
- [ ] Create a simple frontend (React/Vue/Svelte)
- [ ] Add authentication (Spring Security / JWT)

---

## 👨‍💻 Author

**Ossi**  
A project to learn Spring Boot, REST APIs, and test automation.

---

## 🧾 License

This project is licensed under the MIT License – feel free to use and modify it.
