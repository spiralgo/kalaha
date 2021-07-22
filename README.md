# A humble documentation on the Kalaha project.
# DEMO:

The latest version of the Kalaha game project is deployed here:
https://kalahagame.herokuapp.com/
- You can create a player.
- You can join an already existing game as a player or a viewer. 
- You can start a new game.
- As you need an opponent, you can invite a friend or create a player (from another browser) for yourself.

# System Design Diagram
![arch](https://user-images.githubusercontent.com/60903744/125266775-d48d2800-e30e-11eb-9bfa-2920382953ce.png)


# Run the project using Docker:

- `docker build --tag=kalaha:latest .`
- `docker-compose up --build`

In order to stop the project:

- `docker-compose down`

> Note: For Docker configuration, you should set the application mode as "development" in build.gradle:
```javascript
task buildReactApp(type: NodeTask, dependsOn: 'npmInstall') {
    script = project.file('node_modules/webpack/bin/webpack.js')
    args = [
            '--env', 'development',
            '--mode', 'development',
            '--entry', './src/main/webapp/javascript/index.jsx',
            '-o', './src/main/resources/static/dist'
    ]
}
```


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
 
