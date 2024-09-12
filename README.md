# MDD - Social Network for Developers

MDD (Monde de Dév) is a social network dedicated to helping developers connect, collaborate, and find job opportunities. Created with the goal of fostering relationships and collaboration between developers with common interests, MDD aims to be a platform where professionals can network and assist each other in finding job opportunities. It also serves as a potential recruitment hub for companies seeking to fill open positions with talented developers.

This application uses Spring Boot for the backend and Angular for the frontend, combining modern web development technologies to deliver a seamless user experience.

## Versions
* Nodejs 18.20.4
* Npm 10.7.0
* Angular 18.0.5
* Java 21
* MySQL 9.0.1

## Getting Started

### Prerequisites

Before getting started, ensure you have the following installed:

- [Java 21](https://www.oracle.com/java/technologies/downloads/)
- [MySQL](https://www.mysql.com/fr/downloads/)
- [IDE - Development made with Vs code](https://code.visualstudio.com/download)
- Maven
- Angular CLI and Nodes.js
- Java Development Kit (JDK)

### Clone the Project

Clone these repositories :
> git clone https://github.com/oulibouli/Developpez-une-application-full-stack-complete.git

### SQL setup
1. Create database 'mdd'
2. Hibernate will create the tables in the database

### Configure the Application
#### Back-end
1. Open the [`application.properties`](src/main/resources/application.properties) file located in the `api/src/main/resources` directory of the project.
2. Update the following properties with yours:

```properties
# Database configuration
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
# JWT
jwt.secret.key=${JWT_KEY}
```

### Install Dependencies

#### Frontend:

> npm install

#### Backend:

> mvn clean install (use sudo if permission error)

## Run the project

### BackEnd:

> mvn spring-boot:run (use sudo if permission error)

Or run the app with your IDE.

### FrontEnd:

> ng serve

The application is available at http://localhost:4200/.

## API Endpoints

### Authentication
- **POST** `/api/auth/register`
  - Registers a new user with username, email, and password.
  - Request Body: `AuthDTORegister`
  - Response: `AuthDTO`

- **POST** `/api/auth/login`
  - Logs in a user with email or username and password.
  - Request Body: `AuthDTOLogin`
  - Response: `AuthDTO`

- **GET** `/api/auth/me`
  - Retrieves the current logged-in user's profile.
  - Response: `AuthDTO`

- **PUT** `/api/auth/update`
  - Updates the current logged-in user's profile (username, email, or password).
  - Request Body: `AuthDTOUpdate`
  - Response: `Message`

### Posts
- **GET** `/api/posts`
  - Retrieves all posts from topics the user is subscribed to.
  - Response: `List<PostDTO>`

- **GET** `/api/posts/{id}`
  - Retrieves a single post by its ID.
  - Response: `PostDTO`

- **POST** `/api/posts/{id}`
  - Creates a new post under a specific topic.
  - Request Body: `PostDTOCreate`
  - Response: `PostDTO`

### Comments
- **POST** `/api/comment/{postId}`
  - Adds a comment to a specific post.
  - Request Body: `CommentDTOCreate`
  - Response: `CommentDTO`

### Topics
- **GET** `/api/topics`
  - Retrieves all available topics for the user.
  - Response: `List<TopicDTO>`

- **POST** `/api/topics/subscribe/{topicId}`
  - Subscribes the current user to a specific topic.
  - Response: `SubscriptionDTO`

- **POST** `/api/topics/unsubscribe/{topicId}`
  - Unsubscribes the current user from a specific topic.
  - Response: `SubscriptionDTO`

- **GET** `/api/topics/subscriptions`
  - Retrieves all topics the current user is subscribed to.
  - Response: `List<TopicDTO>`

## Main Dependencies

### Frontend (Angular)
- **@angular/core** ^14.1.0 – The core framework for building Angular applications.
- **@angular/material** ^14.2.5 – Angular Material components for UI.
- **rxjs** ~7.5.0 – Library for reactive programming using Observables.
- **jwt-decode** ^4.0.0 – Utility library to decode JWT tokens.
- **typescript** ~4.7.2 – Superset of JavaScript that adds types.

### Backend (Spring Boot)
- **Spring Boot Starter Security** – Provides security features such as authentication and authorization.
- **Spring Boot Starter Data JPA** – For working with JPA and Hibernate.
- **Spring Boot Starter Web** – To build web applications, including RESTful services.
- **MySQL Connector** – JDBC driver for MySQL database integration.
- **io.jsonwebtoken (JJWT)** – For handling JWT (JSON Web Tokens) for authentication.
- **Lombok** – Reduces boilerplate code by generating getter, setter, and constructor methods automatically.

### Testing Dependencies
- **Spring Boot Starter Test** – Provides testing tools and libraries for Spring Boot applications.