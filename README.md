# A humble documentation on the Kahala project.

# Run the project using Docker:

- `docker build --tag=kalaha:latest .`
- `docker-compose up --build`

In order to stop the project:

- `docker-compose down`

# Database configuration:

The reviewer can find the basic database configuration in `application.properties` file.

If Docker will be used:
spring.datasource.url=jdbc:mysql://myapp-mysql:3306/gameStateDB?createDatabaseIfNotExist=true

> Note: If the reviewer would like to test the code on a local MySQL
> server, `spring.datasource.url` property should be assigned with
> "localhost" in this case. :
>
>spring.datasource.url=jdbc:mysql://localhost:3306/gameStateDB?createDatabaseIfNotExist=true

# API Documentation:

The documentation for APIs has been created using Swagger.

The reviewer can access the document via this link:
http://localhost:8080/swagger-ui.html

# How is the code test implemented?

TDD is used as much as possible, in order to achieve a nice percent of test coverage, a readble, meaningful but quite a
minimalistic code.

GameRulesServiceTest is the starting point to code the project as a test-case is provided for each rule of the game.
 
 
# TODO: further possible improvements
 - Seperating the monolith into microservices. Creating another repo for the React UI.
 - A user management system to construct an authorization and authentication system starting with a login and register form.
 - A more easy-to-use UI with sounds and animations.
 - A mobile client preferably coded with Flutter. That will use the same Spring Boot server via REST services and WebSocket.
 
