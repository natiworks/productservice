<!-- 
  REFLECTION.md
  Lab 1 — Hello Enterprise World with Spring Boot
  Enterprise Application Development — CTBE, Addis Ababa University
-->

<!-- 
  Question 1:
  What is the purpose of the Service layer? Why should a Controller never call a Repository directly?

  Answer:
  The Service layer is where the main logic of the application is placed. It sits between
  the Controller and the Repository and connects them together.

  The Controller should only deal with handling HTTP requests and sending responses.
  It should not worry about how data is saved or retrieved from the database.

  If a Controller calls a Repository directly, the code becomes messy because business
  logic and request handling get mixed together. By using the Service layer, we keep the
  code organized. If we need to change how something works, we only update it in the
  Service instead of changing many controllers.
-->

<!-- 
  Question 2:
  Why do enterprise systems use layered architecture? What problems does it solve
  compared to writing all logic in one class?

  Answer:
  Enterprise systems use layered architecture to keep the code organized and easier to manage.

  Each layer has a specific job. The Controller handles web requests, the Service contains
  business logic, and the Repository manages database access.

  If everything were written in one class, the code would become large and confusing.
  It would also be harder to fix bugs, test the program, or change parts of the system.
  Layered architecture makes the system easier to maintain and easier for teams to work on.
-->

<!-- 
  Question 3:
  What advantages does Spring Data JPA provide over writing plain JDBC code?
  Give at least two specific examples.

  Answer:
  Spring Data JPA makes working with databases much easier compared to traditional JDBC.

  First, it removes a lot of repetitive code. Instead of writing SQL queries and manually
  handling database connections, we can simply call methods like findById().

  Second, it can create queries automatically from method names. For example, a method like
  findByNameContainingIgnoreCase() will automatically generate the correct SQL query
  without us writing any SQL at all.

  Third, it can create database tables automatically from entity classes using Hibernate,
  which saves a lot of time during development.
-->

<!-- 
  Question 4:
  In your unit test, you used a mock for ProductRepository. Why is it important to isolate
  the class under test from its real dependencies?

  Answer:
  When testing a class, it is important to focus only on that class. Real dependencies like
  databases can slow down tests and sometimes cause unpredictable results.

  By using mocks, such as mocking the ProductRepository with Mockito, we can control exactly
  what the dependency returns. This allows us to test the logic of the service without
  needing a real database to be running.

  This makes tests faster, more reliable, and easier to run consistently.
-->

<!-- 
  Question 5:
  What would happen if spring.jpa.hibernate.ddl-auto were set to update instead of
  create-drop? When would you use each setting?

  Answer:
  When create-drop is used, the database tables are created every time the application
  starts and deleted when the application stops. This is helpful during development
  because it gives us a clean database each time we run the app.

  When update is used, the database schema is adjusted based on the entity classes,
  but existing data is kept. This is useful when we do not want to lose our data
  between restarts.

  In real production systems, developers usually manage database changes using dedicated
  tools like Flyway or Liquibase instead of relying on Hibernate's ddl-auto setting,
  because those tools are safer and more controlled.
-->