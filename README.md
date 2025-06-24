# Play Framework Guide

A comprehensive guide to building web applications with the Play Framework - a Java web application framework based on the Model-View-Controller (MVC) architecture.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Project Structure](#project-structure)
- [Plugins and Dependencies](#plugins-and-dependencies)
- [Creating Your First Endpoint](#creating-your-first-endpoint)
- [Templates](#templates)
- [Database Integration with Ebean](#database-integration-with-ebean)
- [CRUD API Example](#crud-api-example)

## Overview

Play framework is a lightweight, stateless, web-friendly architecture that uses Akka and Akka Streams to provide predictable and minimal resource consumption (CPU, memory, threads). Thanks to its reactive model, applications scale naturally–both horizontally and vertically.

Play includes all the components needed to build Web Applications and REST services, including:
- Integrated HTTP server
- Form handling
- Cross-Site Request Forgery (CSRF) protection
- Powerful routing mechanism
- I18n support

**Current Support:** Java 11 only

## Features

- **Hot reloading**: Changes to code are immediately reflected in the browser
- **Scala and Java support**: Versatile framework supporting both languages
- **Lightweight**: Easy to deploy and scale
- **RESTful**: Built for API development and service integration
- **Asynchronous**: Highly scalable and efficient
- **Testing support**: Built-in testing capabilities

## Architecture

Play follows the **Model-View-Controller (MVC)** pattern:

- **Model**: Represents data and business logic
- **View**: Represents the user interface
- **Controller**: Handles user input and coordinates between model and view

The framework uses a routing mechanism to map URLs to controllers and actions based on HTTP method, URL path, and request parameters.

## Prerequisites

- Java 11
- SBT (Simple Build Tool)

## Setup

1. **Install SBT** (Simple Build Tool)

2. **Create a new project**
   ```bash
   # Navigate to your desired directory
   sbt new playframework/play-java-seed.g8
   ```
   
3. **Specify project details** when prompted (project name, package name)

4. **Run the application**
   ```bash
   sbt run
   ```

> **Note for IntelliJ Users**: Do not add play configuration. Run it from the built-in sbt shell.

## Project Structure

```
your-project/
├── app/                    # Main source code
│   ├── controllers/        # HTTP request handlers
│   ├── models/            # Data models and business logic
│   ├── views/             # View templates
│   └── assets/            # Static assets (CSS, JS, images)
├── conf/                  # Configuration files
│   ├── application.conf   # Main configuration
│   ├── routes            # URL routing definitions
│   └── logback.xml       # Logging configuration
├── public/               # Publicly accessible static files
├── test/                # Test files
├── project/             # Build-related files and plugins
├── target/              # Compiled code and build artifacts
├── build.sbt           # Build configuration
└── plugins.sbt         # SBT plugins (in project/ directory)
```

### Key Configuration Files

- **`build.sbt`**: Defines build configuration including dependencies
- **`plugins.sbt`**: Located in `project/` directory, manages SBT plugins
- **`routes`**: Maps URLs to controller actions
- **`application.conf`**: Contains database connections, logging settings, etc.

## Plugins and Dependencies

### Adding a Plugin

1. **Add to `plugins.sbt`**:
   ```scala
   addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "6.0.0")
   ```

2. **Enable in `build.sbt`**:
   ```scala
   lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
   ```

### Adding Dependencies

```scala
libraryDependencies ++= Seq(
  "org.projectlombok" % "lombok" % "1.18.26",
  guice
)
```

## Creating Your First Endpoint

### 1. Define Route

Add to `conf/routes`:
```
GET    /home/:fName/:lName    controllers.BasicController.homepage(fName: String, lName: String)
```

### 2. Create Controller

```java
import play.mvc.Controller;
import play.mvc.Result;

public class BasicController extends Controller {
    public Result homepage(String fName, String lName) {
        return ok("Welcome Home! \n User -> " + fName + " " + lName);
    }
}
```

### 3. Using Templates (Optional)

**Create view template** (`views/homepage.scala.html`):
```html
@(fName: String, lName: String)

<!DOCTYPE html>
<html>
    <head>
        <title>Basic View</title>
    </head>
    <body>
        <h1>Hello, @fName @lName!</h1>
    </body>
</html>
```

**Update controller**:
```java
import views.html.homepage;

public class BasicController extends Controller {
    public Result homepage(String fName, String lName) {
        return ok(homepage.render(fName, lName));
    }
}
```

### Result Class Methods

- `ok()`: Creates 200 OK response
- `status()`: Sets specific status code
- `withHeader()`: Adds headers
- `withJson()`: Sets JSON response body
- `as()`: Sets content type

## Templates

Play templates use **Twirl** templating engine with HTML and Scala/Java code:

```html
<!DOCTYPE html>
<html>
  <head>
    <title>Play Template Example</title>
  </head>
  <body>
    <h1>Welcome to Play Template Example</h1>

    @for(i <- 1 to 5) {
      <p>Number: @i</p>
    }
  </body>
</html>
```

**Template Features:**
- Data binding from controllers
- Template inheritance
- Build-time compilation
- Template helpers and utilities
- Control structures and expressions

## Database Integration with Ebean

Ebean is Play's default ORM (Object-Relational Mapping) framework.

### Setup Database Connection

1. **Add PostgreSQL dependency**:
   ```scala
   libraryDependencies += "org.postgresql" % "postgresql" % "42.5.4"
   ```

2. **Configure in `application.conf`**:
   ```
   db.default.driver = "org.postgresql.Driver"
   db.default.url = "jdbc:postgresql://localhost:5432/mydatabase"
   db.default.username = your_username
   db.default.password = your_password
   ```

### Entity Model with Finder

```java
@Entity
public class User extends Model {
    @Id
    private Long id;
    private String name;
    private String email;

    // Finder for database operations
    public static final Finder<Long, User> find = new Finder<>(User.class);
    
    // Getters and setters...
}
```

### Using Finder for Queries

```java
// Find by ID
User user = User.find.byId(1L);

// Complex query
List<User> users = User.find.query()
    .where()
    .like("name", "%John%")
    .findList();
```

## CRUD API Example

### Controller Implementation

```java
public class BasicController extends Controller {
    
    public Result getAllEmployee() {
        List<Employee> employeeList = Employee.employeeFinder.all();
        return ok(Json.toJson(employeeList));
    }

    public Result saveEmployee(Http.Request request) {
        Employee employee = Json.fromJson(request.body().asJson(), Employee.class);
        employee.save();
        return ok("Employee saved");
    }

    public Result getEmployeeById(Long id) {
        return ok(Json.toJson(Employee.employeeFinder.ref(id)));
    }

    public Result updateEmployee(Http.Request request) {
        Employee employee = Json.fromJson(request.body().asJson(), Employee.class);
        Employee existingEmployee = Employee.employeeFinder.ref(employee.getId());
        existingEmployee.setDesignation(employee.getDesignation());
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.update();
        return ok("Employee updated");
    }

    public Result deleteEmployee(Long id) {
        Employee existingEmployee = Employee.employeeFinder.ref(id);
        existingEmployee.delete();
        return ok("Employee deleted");
    }
}
```

### Routes Configuration

```
GET     /employee/all              controllers.BasicController.getAllEmployee
POST    /employee/save             controllers.BasicController.saveEmployee(request: Request)
GET     /employee/:id              controllers.BasicController.getEmployeeById(id: Long)
PUT     /employee/update           controllers.BasicController.updateEmployee(request: Request)
DELETE  /employee/:id              controllers.BasicController.deleteEmployee(id: Long)
```

## Getting Started

1. Clone or create a new Play project
2. Configure your database connection
3. Create your entity models
4. Define your routes
5. Implement your controllers
6. Run with `sbt run`
7. Access your application at `http://localhost:9000`

## Additional Resources

- [Play Framework Documentation](https://www.playframework.com/documentation)
- [Ebean ORM Documentation](https://ebean.io/)
- [SBT Documentation](https://www.scala-sbt.org/documentation.html)

---

**Happy coding with Play Framework!** 🚀
