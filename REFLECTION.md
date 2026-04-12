# Reflection — Lab 1: Hello Enterprise World with Spring Boot

**Course:** Enterprise Application Development  
**University:** Addis Ababa University — CTBE

---

## Question 1
**What is the purpose of the Service layer? Why should a Controller never call a Repository directly?**

The Service layer is where the main logic of the application is placed. It sits between the Controller and the Repository and connects them together.

The Controller should only deal with handling HTTP requests and sending responses. It should not worry about how data is saved or retrieved from the database.

If a Controller calls a Repository directly, the code becomes messy because business logic and request handling get mixed together. By using the Service layer, we keep the code organized. If we need to change how something works, we only update it in the Service instead of changing many controllers.

---

## Question 2
**Why do enterprise systems use layered architecture? What problems does it solve compared to writing all logic in one class?**

Enterprise systems use layered architecture to keep the code organized and easier to manage.

Each layer has a specific job. The Controller handles web requests, the Service contains business logic, and the Repository manages database access.

If everything were written in one class, the code would become large and confusing. It would also be harder to fix bugs, test the program, or change parts of the system. Layered architecture makes the system easier to maintain and easier for teams to work on.

---

## Question 3
**What advantages does Spring Data JPA provide over writing plain JDBC code? Give at least two specific examples.**

Spring Data JPA makes working with databases much easier compared to traditional JDBC.

First, it removes a lot of repetitive code. Instead of writing SQL queries and manually handling database connections, we can simply call methods like `findById()`.

Second, it can create queries automatically from method names. For example, a method like `findByNameContainingIgnoreCase()` will automatically generate the correct SQL query without us writing any SQL at all.

Third, it can create database tables automatically from entity classes using Hibernate, which saves a lot of time during development.

---

## Question 4
**In your unit test, you used a mock for ProductRepository. Why is it important to isolate the class under test from its real dependencies?**

When testing a class, it is important to focus only on that class. Real dependencies like databases can slow down tests and sometimes cause unpredictable results.

By using mocks, such as mocking the `ProductRepository` with Mockito, we can control exactly what the dependency returns. This allows us to test the logic of the service without needing a real database to be running.

This makes tests faster, more reliable, and easier to run consistently.

---

## Question 5
**What would happen if `spring.jpa.hibernate.ddl-auto` were set to `update` instead of `create-drop`? When would you use each setting?**

When `create-drop` is used, the database tables are created every time the application starts and deleted when the application stops. This is helpful during development because it gives us a clean database each time we run the app.

When `update` is used, the database schema is adjusted based on the entity classes, but existing data is kept. This is useful when we do not want to lose our data between restarts.

In real production systems, developers usually manage database changes using dedicated tools like Flyway or Liquibase instead of relying on Hibernate's ddl-auto setting, because those tools are safer and more controlled.
# Reflection — Lab 2
---
## Question 1
**Why should the ProductRequest DTO carry the @Valid annotations instead of the Product entity itself?**

The `ProductRequest` DTO holds the data that the client sends to the API, so it makes sense to check and validate that data right there before anything else happens.

The `Product` entity is mainly for saving data to the database. If we also put validation rules inside the entity, it would have two jobs at once — database mapping and input validation — which makes it harder to manage.

There is also a safety reason. The entity has fields like `id` that the database generates automatically. We do not want clients to be able to send or change those values. By using a DTO that only includes the fields the client is allowed to provide, we have better control over what comes into the system.

---

## Question 2
**What is the purpose of the Location header returned on a POST 201 Created response, and which HTTP specification mandates it?**

When a client creates a new resource using a POST request, the server responds with **201 Created**. Along with that response, the server also sends a **Location header** that contains the URL where the newly created resource can be found.

For example, it might look like:  
`http://localhost:8080/api/v1/products/4`

This is helpful because the client immediately knows where to find or access the new resource without having to search for it.

This behavior is described in the HTTP standard **RFC 9110**. In our controller, we used **ServletUriComponentsBuilder** to build that URL automatically by taking the current request URL and adding the new resource id to it.

---

## Question 3
**Explain the difference between @ControllerAdvice and @ExceptionHandler. When would you use each?**

`@ExceptionHandler` is an annotation we put on a method to handle a specific type of exception. When that exception is thrown, Spring automatically calls that method to deal with it.

The problem is that by default it only works inside the controller where it is written. So if the same error can happen in multiple controllers, we would have to copy the same handler everywhere.

That is where `@ControllerAdvice` helps. When we put this on a separate class, all the `@ExceptionHandler` methods inside it will work across the entire application — not just one controller.

So in short: use `@ExceptionHandler` alone for a single controller, and use `@ControllerAdvice` when you want one place to handle errors for the whole API.

---

## Question 4
**In your MockMvc tests you used @Transactional on the test class. What would happen to the database state between tests if you removed this annotation?**

If we remove `@Transactional`, any data that a test saves to the database will stay there after the test finishes.

This means the next test will see data left behind from the previous one. As more tests run, the database fills up with leftover data. This can cause tests to fail or give wrong results just because of data from earlier tests, not because of any real bug.

When `@Transactional` is used on the test class, each test runs inside a transaction that is automatically rolled back when the test ends. So the database goes back to how it was before, and every test starts fresh and clean. This makes tests much more reliable.

---

## Question 5
**What does RFC 9457 define, and why does following it produce better APIs than returning a generic error message?**

**RFC 9457** is a standard that defines a format called **Problem Details** for sending error responses in HTTP APIs.

Instead of returning something vague like `{ "error": "something went wrong" }`, the response includes useful structured fields such as:

- `type` — the kind of error that happened
- `title` — a short description of the problem
- `status` — the HTTP status code
- `detail` — a clear explanation of what specifically went wrong

This is better for everyone involved. Clients can understand and handle different errors properly. Developers get clear information when debugging. It removes the confusion that comes with generic messages and makes the API much easier to work with.

---

## Question 6
**What is the difference between an integration test (MockMvc) and a unit test (Mockito)? When is each approach preferable?**

A **unit test** tests one class on its own. We use tools like **Mockito** to replace real dependencies with fake ones so we can focus only on the logic of that one class. Unit tests run very fast because they do not need a database or a server.

An **integration test** checks how different parts of the application work together. With **MockMvc**, the full Spring application context is loaded including the controller, service, repository, and database. A fake HTTP request is sent and goes through the entire system just like a real one would.

Unit tests are best when you want to quickly test a small piece of logic in isolation. Integration tests are better when you want to make sure the whole system works correctly end to end, from the API request all the way to the database.