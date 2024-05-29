# Train Ticketing System

This project is a Java-based train ticketing system, created as part of a technical assessment. It includes several API endpoints for managing tickets and user data.

## Features

- Purchase train tickets
- Cancel train tickets
- View receipt details
- View users by section
- Remove a user
- Modify a user's seat

## Technologies Used

- Java 17
- Spring Boot 3.3.0
- JUnit 5 for testing

## Prerequisites

- Java 17
- Maven

## API Endpoints
Here is a list of the API endpoints provided by the application:

### Purchase Ticket

- **URL:** `/api/tickets/purchase`
- **Method:** `POST`
- **Description:** Purchase a train ticket.
- **Request Body:**
  ```json
  {
      "firstName": "John",
      "lastName": "Doe",
      "age": 30,
      "gender": "Male",
      "email": "john.doe@example.com",
      "id": "unique-identifier"
  }
- **Response:**
  ```json
  {
    "pnr": 123456789,
    "seat": "A1",
    "passenger": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "age": 30,
        "gender": "Male",
        "email": "john.doe@example.com"
    }
  }

### Cancel Ticket

- **URL:** `/api/tickets/cancel/{pnr}`
- **Method:** `DELETE`
- **Description:** Cancel a train ticket.
  

### View Receipt Details

- **URL:** `/api/tickets/receipt/{pnr}`
- **Method:** `GET`
- **Description:** View receipt details for a ticket.
- **Response:**
  ```json
  {
    "pnr": 123456789,
    "seat": "A1",
    "passenger": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "age": 30,
        "gender": "Male",
        "email": "john.doe@example.com"
    }
  }



### View Users by Section

- **URL:** `/api/users/section/{section}`
- **Method:** `GET`
- **Description:** View users and their allocated seats by section.
- **Response:**
  ```json
  [
    {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "age": 30,
        "gender": "Male",
        "seat": "A1",
        "email": "john.doe@example.com"
    }
  ]

## Author

- Tejas Drolia












