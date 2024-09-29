
## Overview

This project is a Spring Boot application designed to manage `Employee`, `Department`, and `Address` entities. It utilizes technologies such as **Spring Boot**, **MongoDB**, **MySQL**, and includes a comprehensive testing suite for the Controller, Service, and Repository layers.

## Features

- **Layered Architecture**: The project is organized into three main layers:
  - **Controller Layer**: Handles incoming HTTP requests and returns appropriate responses.
  - **Service Layer**: Contains business logic and interacts with the Repository layer for data access.
  - **Repository Layer**: Manages data persistence and interactions with the database.

- **Comprehensive Testing**: All layers are thoroughly tested using mocking and assertions to ensure the application works as expected.

## Technologies Used

- **Spring Boot** for building the application.
- **JUnit** for unit testing.
- **Mockito** for mocking dependencies during testing.

## Testing

### Testing Overview

- The project includes tests for each layer:
  - **Controller Tests**: Validate API endpoints and ensure correct responses using mocks.
  - **Service Tests**: Verify business logic and interactions between services using mocking.
  - **Repository Tests**: Test data access operations and database interactions.


