# yanosik-test-task

main functionality was implemented according to the task description (`task_description/Task_PL.png`).

You can see the workflow of the user request in the console when you run the app.

I used MySQL for this task.

Used dependencies:
* Spring context for more convenient dependency injection;
* JSON dependency for more convenient JSON parsing/composing;
* I am also using a Google code style convention and a checkstyle plugin for code quality;

---
## How to run

1. Run the SQL script to set up the database with mock data (`src/main/resources/init_db.sql`);

2. To set the server online, you have to run the main method in the `UserRequestListener` class (`src/main/java/org/yanosik/test/task/server/UserRequestListener.java`);

3. After that you can run the client, which was implemented through the test class (`src/test/java/org/yanosik/test/task/Client.java`) to have it all in one convenient place.

---
## Modifications and implemented features

* database structure:
  * changed vehicles foreign key from `user_login` to `user_id`, because login is not a primary key, so it can not be referenced as a foreign key in a 3NF database
  * implemented a soft delete for entities
  * Suggested changes: 
    * make a separate table for relations between `users`, `vehicles`, and `vehicles`,`insuranceOffers` 
      with `id` of the entities as foreign keys; 
    * user password field should store already hashed password + salt;
    * user login field should be unique too (we need this to authorize users correctly);
    * More about the database in the `UserDaoImpl` documentation: `src/main/java/org/yanosik/test/task/server/dao/impl/UserDaoImpl.java`.
* User Authorization:
  * `AuthenticationService` was implemented with basic logic;
  * More about it in the `AuthenticationServiceImpl` documentation: `src/main/java/org/yanosik/test/task/server/service/impl/AuthenticationServiceImpl.java`.
* Serving multiple clients:
  * Each socket can handle multiple client requests, and for each client connection, 
    a separate thread is created to simultaneously serve multiple clients.

---
Also, inside the code you can find quite a lot of comments on how the things work 
and the reasons of some decisions that I made.
