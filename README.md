User Relationship Service
A Spring Boot REST API for managing users and their mutual friendships, including a computed popularity score and a graph-style view of relationships. The service uses Spring Web and Spring Data JPA, with PostgreSQL as the runtime database driver and Lombok for boilerplate reduction.​

Key features
CRUD operations for users via REST endpoints.​

Create and remove mutual friendships between users with validations to prevent invalid links and duplicates.​

Computed popularity score per user based on friend count and shared hobbies.​

Consistent error responses via a global exception handler.​

JPA repository abstraction for persistence.​

Tech stack
Spring Boot 3.5.x (parent).​

Spring Web, Spring Data JPA.​

PostgreSQL driver (runtime).​

Lombok (optional).​

Java 21.​

Maven build with Spring Boot Maven Plugin.​

Project structure
Application entry point: AssignmentApplication.​

REST layer: UserController at base path /api/users.​

Service layer: UserService implementing business logic and popularity score calculation.​

Persistence: UserRepository extending JpaRepository<User, String>.​

Error handling: GlobalExceptionHandler with structured error responses.​

Custom exceptions: BadRequestException, ResourceNotFoundException, InternalServerErrorException.​

API endpoints
Base path: /api/users.​

GET /api/users — List all users with computed popularity scores applied on retrieval.​

POST /api/users — Create a new user from JSON body.​

PUT /api/users/{id} — Update username, age, and hobbies for an existing user.​

DELETE /api/users/{id} — Delete a user only if they have no friends; otherwise an error is raised instructing to unlink first.​

POST /api/users/{id}/link?friendId={friendId} — Create a mutual friendship; prevents self-link and duplicate friendships.​

DELETE /api/users/{id}/unlink?friendId={friendId} — Remove a mutual friendship between two users.​

GET /api/users/graph — Return a graph-style projection of users with node details (id, username, age, hobbies, friend ids, createdAt, popularityScore).​
