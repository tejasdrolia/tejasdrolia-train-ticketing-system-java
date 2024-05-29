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

## Prerequisites

- Java 17
- Maven

## API Endpoints
Here is a list of the API endpoints provided by the application:

### Purchase Ticket

- **URL:** `/api/ticket/purchase`
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
      "id": "unique-identifier" //no need to pass 
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
    "from": "London",
    "to": "France",
    "price": 20.0,
    "seat": "A43" //some random seat
  }

### Cancel Ticket

- **URL:** `/api/ticket/cancel/{pnr}`
- **Method:** `DELETE`
- **Description:** Cancel a train ticket.
- **Response:**
  Ticket cancelled successfully for PNR: {PNR}
  

### View Receipt Details for a single ticket

- **URL:** `/api/ticket/{pnr}`
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
    "from": "London",
    "to": "France",
    "price": 20.0,
    "seat": "A45"
  }



### View Passengers by Section

- **URL:** `/api/ticket/view/{section}`
- **Method:** `GET`
- **Description:** View passengers on the basis of section.
- **Response:**
 Array of Passenger Objects

### Get Available seats by section

- **URL:** `/api/ticket/available_seats/{section}`
- **Method:** `GET`
- **Description:** Get available seats by section.
- **Response:**
  Array of Strings representing seats.


### Modify seats of a passenger

- **URL:** `/api/ticket/modify/{pnr}`
- **Method:** `PUT`
- **Description:** Modifies seats of user (if available).
- **REQUEST:** Need to pass the desired seat Number through body (eg:B23)
- **Response:**
  Passenger object along with changed seat.


### Get all the seats booked by a single user

- **URL:** `/api/user/{user_id}/tickets`
- **Method:** `GET`
- **Description:** Get all the seats booked by a user.
- **Response:**
  Array of Ticket Objects.


### Get user details

- **URL:** `/api/user/{user_id}`
- **Method:** `GET`
- **Description:** Get all details of a user.
- **Response:**
  Passenger(User) object is returned.


## Author

- Tejas Drolia












