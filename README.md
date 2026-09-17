# Self Scheduling System

A simple, full-stack **Self Scheduling System** built as a college DevOps subject activity. Users can create, view, edit, search, and cancel/delete their own schedules/appointments through a clean web UI.

This is a **single Maven-based Spring Boot project** (no separate frontend project) that you can open directly in IntelliJ IDEA, run, and later package/deploy as a runnable JAR.

---

## 1. Project Description

The Self Scheduling System lets a user:

- View a dashboard with a welcome message, total schedule count, and upcoming schedules.
- Create a new schedule with name, email, title, description, date, start/end time, category, and gender.
- View all schedules in a searchable/filterable table.
- Edit an existing schedule.
- Delete/cancel a schedule.
- Search schedules by title or name, and filter by date.

**Unique UI feature:** When creating or editing a schedule, selecting **Male** switches the page to a **dark navy/blue-purple theme**, and selecting **Female** switches it to a **light pink theme**. This is a **purely visual** effect — gender never changes functionality, permissions, or behavior anywhere in the app.

---

## 2. Features

- ✅ Dashboard with live stats (total + upcoming schedules)
- ✅ Create / Read / Update / Delete (CRUD) schedules
- ✅ Search by title or name
- ✅ Filter by date
- ✅ Server-side validation with friendly error messages
- ✅ Gender-based UI theme (visual only)
- ✅ REST API (`/api/schedules`) in addition to the Thymeleaf pages
- ✅ Sample data auto-seeded on first run
- ✅ Responsive layout for smaller screens

---

## 3. Technology Stack

| Layer          | Technology                          |
|----------------|--------------------------------------|
| Language       | Java 17                              |
| Framework      | Spring Boot 3.3.x                    |
| Build Tool     | Maven                                |
| Web            | Spring Web (MVC + REST)              |
| Persistence    | Spring Data JPA + Hibernate          |
| Database       | MySQL 8                              |
| Templating     | Thymeleaf                            |
| Frontend       | HTML5, CSS3, Vanilla JavaScript      |
| Validation     | Jakarta Bean Validation               |
| Testing        | JUnit 5, Spring Boot Test, Mockito   |

---

## 4. Requirements

Before you start, make sure you have installed:

- **JDK 17** (or newer, configured as JDK 17 language level)
- **Maven 3.8+** (IntelliJ has an embedded Maven, so a separate install is optional)
- **MySQL 8.x** Server running locally
- **IntelliJ IDEA** (Community or Ultimate)

---

## 5. MySQL Database Setup

1. Start your local MySQL server.
2. Open a MySQL client (MySQL Workbench, terminal, or IntelliJ's Database tool) and create the database:

   ```sql
   CREATE DATABASE IF NOT EXISTS self_scheduling_db;
   ```

   > Note: `application.properties` already includes `createDatabaseIfNotExist=true`, so this step is optional — Spring Boot will create the database automatically if it does not exist, as long as your MySQL user has permission to create databases.

3. Open `src/main/resources/application.properties` and set your **own MySQL password**:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/self_scheduling_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

   Replace `YOUR_PASSWORD` with your actual local MySQL root (or other user's) password. **Do not commit your real password to version control** — for a real project you'd typically use an environment variable instead.

4. Tables are created/updated automatically because `spring.jpa.hibernate.ddl-auto=update` is set — you do not need to write any DDL by hand.

---

## 6. How to Open in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Click **File → Open...** and select the `self-scheduling-system` folder (the one containing `pom.xml`).
3. IntelliJ will detect it as a Maven project and prompt to **load/import** it — accept, and let Maven download all dependencies (this may take a minute the first time).
4. Go to **File → Project Structure → Project** and set the **SDK to Java 17** (and the language level to 17).
5. Wait for the Maven indicator (bottom right) to finish syncing.

---

## 7. How to Run Using IntelliJ

1. Open `src/main/java/com/selfscheduling/SelfSchedulingApplication.java`.
2. Click the green **Run ▶** icon next to the `main` method (or right-click the file → **Run 'SelfSchedulingApplication'**).
3. Wait for the console to show `Started SelfSchedulingApplication`.
4. Open a browser at: **http://localhost:8080/**

On first run, three sample schedules are automatically inserted so you have data to demo immediately.

---

## 8. How to Run Using Maven (Command Line)

Open a terminal inside the project folder (`self-scheduling-system/`) and run:

```bash
# Clean any previous build output
mvn clean

# Run unit tests
mvn test

# Package the application into a JAR (also runs tests)
mvn package

# Do a full clean + package in one command
mvn clean package
```

A successful `mvn clean package` produces:

```
target/self-scheduling-system.jar
```

---

## 9. How to Run the Generated JAR

After `mvn clean package` completes successfully:

```bash
java -jar target/self-scheduling-system.jar
```

Then open **http://localhost:8080/** in your browser. This proves the application runs independently of IntelliJ, using only the packaged JAR — exactly what you need to demonstrate for the DevOps activity.

---

## 10. Simple Deployment Procedure (Windows / College Demo)

This satisfies the DevOps requirement (Maven build → package → JAR → run/deploy) without needing Docker, Jenkins, or Kubernetes:

1. **Build the project:**
   ```powershell
   mvn clean package
   ```
2. **Locate the JAR** in the `target/` folder:
   ```
   target\self-scheduling-system.jar
   ```
3. **Copy the JAR** to wherever you want to "deploy" it (e.g., a `deploy/` folder on your Desktop, or a different machine on the same network).
4. Make sure the target machine has:
   - Java 17 installed
   - MySQL running with the `self_scheduling_db` database available (or update `application.properties` before building, or externalize DB config using `--spring.config.location` or environment variables)
5. **Run the deployed JAR:**
   ```powershell
   java -jar self-scheduling-system.jar
   ```
6. Open `http://localhost:8080/` (or the deployment machine's IP if run remotely) to access the running application.

This demonstrates the full DevOps build pipeline: **source code → Maven build → tests → packaged artifact (JAR) → deployment → running application.**

---

## 11. Project Structure

```
self-scheduling-system/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/selfscheduling/
│   │   │   ├── SelfSchedulingApplication.java
│   │   │   ├── config/
│   │   │   │   └── DataSeeder.java
│   │   │   ├── controller/
│   │   │   │   ├── ScheduleRestController.java
│   │   │   │   └── ScheduleViewController.java
│   │   │   ├── service/
│   │   │   │   └── ScheduleService.java
│   │   │   ├── repository/
│   │   │   │   └── ScheduleRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── Schedule.java
│   │   │   │   ├── Gender.java
│   │   │   │   └── ScheduleStatus.java
│   │   │   └── dto/
│   │   │       └── ScheduleRequest.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── dashboard.html
│   │       │   ├── schedule-list.html
│   │       │   ├── schedule-form.html
│   │       │   └── error.html
│   │       ├── static/
│   │       │   ├── css/style.css
│   │       │   └── js/
│   │       │       ├── theme.js
│   │       │       └── script.js
│   │       └── application.properties
│   └── test/
│       └── java/com/selfscheduling/
│           ├── SelfSchedulingApplicationTests.java
│           └── ScheduleServiceTest.java
```

---

## 12. REST API Reference

| Method | Endpoint                     | Description                          |
|--------|-------------------------------|---------------------------------------|
| GET    | `/api/schedules`              | Get all schedules (supports `?keyword=` and `?date=YYYY-MM-DD`) |
| GET    | `/api/schedules/{id}`         | Get one schedule by id                |
| POST   | `/api/schedules`               | Create a new schedule                 |
| PUT    | `/api/schedules/{id}`         | Update an existing schedule           |
| DELETE | `/api/schedules/{id}`         | Delete a schedule                     |
| PATCH  | `/api/schedules/{id}/cancel`  | Mark a schedule as cancelled          |

### Web Pages (Thymeleaf)

| Method | Route                     | Description         |
|--------|----------------------------|----------------------|
| GET    | `/`                        | Dashboard            |
| GET    | `/schedules`               | Schedule list (search/filter) |
| GET    | `/schedules/new`           | Create schedule form |
| POST   | `/schedules/new`           | Submit new schedule  |
| GET    | `/schedules/{id}/edit`     | Edit schedule form   |
| POST   | `/schedules/{id}/edit`     | Submit schedule update |
| POST   | `/schedules/{id}/delete`   | Delete/cancel schedule |

---

## 13. Validation Rules

- Name cannot be empty
- Email must be a valid email format
- Title cannot be empty
- Date cannot be empty
- Start time cannot be empty
- End time cannot be empty
- End time must be after start time (checked both in the service layer and enforced through friendly form error messages)

---

## 14. Sample Data

On first startup (only if the `schedules` table is empty), the app inserts:

- **College Project Meeting** — Academic, Male theme sample
- **Study Session** — Study, Female theme sample
- **Team Discussion** — Work, Male theme sample

This lets you demo the dashboard and list views immediately without manual data entry.

---

## 15. Screenshots

> Add your own screenshots here when demonstrating the project.

- Dashboard (default theme): `screenshots/dashboard.png`
- Schedule list: `screenshots/schedule-list.png`
- Create form — Male (dark) theme: `screenshots/create-male-theme.png`
- Create form — Female (pink) theme: `screenshots/create-female-theme.png`

---

## 16. DevOps Workflow Explanation

This project demonstrates a minimal, real-world DevOps build/release flow using **Maven** as the core tool:

1. **Source Control** – The project lives in a standard Maven directory layout, ready to be committed to Git.
2. **Dependency Management** – All dependencies (Spring Web, Spring Data JPA, Thymeleaf, MySQL driver, etc.) are declared in `pom.xml` and resolved automatically by Maven.
3. **Build (`mvn clean`)** – Removes old compiled classes/artifacts from `target/` to guarantee a fresh build.
4. **Test (`mvn test`)** – Runs the JUnit test suite (`SelfSchedulingApplicationTests`, `ScheduleServiceTest`) to verify the app boots correctly and core business logic behaves as expected.
5. **Package (`mvn package` / `mvn clean package`)** – Compiles the code, runs tests, and bundles everything into a single **executable JAR** (`target/self-scheduling-system.jar`) using the Spring Boot Maven Plugin.
6. **Deploy** – The JAR is a self-contained artifact: copy it anywhere with Java 17 + MySQL available, then run it with `java -jar`.
7. **Run** – The application starts an embedded Tomcat server on port 8080, so no external application server is needed.

This mirrors the same principles used in real CI/CD pipelines (build → test → package → deploy → run), just performed manually for this college demonstration instead of through Jenkins/GitHub Actions.

---

## 17. Notes

- Gender is used **only** for the visual theme (dark navy/blue-purple for Male, light pink for Female). It has no effect on data access, permissions, or any business logic.
- This project intentionally avoids authentication, microservices, Docker, and other enterprise complexity to stay appropriate for a college DevOps activity focused on **Maven build & deployment**.
