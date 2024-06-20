# Olympic Games 📯

## Description

This project is an application for managing the Olympic Games, developed as part of a university project. It allows managing delegations, participants, sports infrastructures, events, spectators, tickets, and controllers. The application also provides functionalities to view results and the overall ranking.

## Technologies Used

- ☕ **Java 11**
- 🌱 **Spring Boot 2.5.4**
- 📦 **Spring Data JPA**
- 🐘 **Hibernate**
- 🍃 **Lombok**
- 🛠️ **Maven**
- 🗄️ **MySQL** (for in-memory testing)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/JeanGrd/JeuxOlympique
   ```
2. Navigate to the project directory:
   ```bash
   cd your-repo
   ```
3. Compile and package the project:
   ```bash
   mvn clean package
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

*Note: No web interface has been developed. If you want to try the application, you need to use an API platform like Postman.*

## Database Structure

### Main Entities

- **Delegation**: Represents a group participating in the games. Each delegation can have multiple participants.
- **Participant**: Represents an individual participating in events as part of a delegation.
- **Sports Infrastructure**: Represents the sports facilities where events are held.
- **Event**: Represents an event in the games.
- **Spectator**: Represents a spectator who can book tickets for events.
- **Ticket**: Represents a ticket booked by a spectator for an event.
- **Controller**: Represents a controller who validates tickets.
- **Participation**: Represents the participation of a delegation in an event.
- **Result**: Represents the result of a participant in an event.

## Conclusion

The Olympic Games application offers a comprehensive solution for managing and administering various aspects of a large-scale sports event. Leveraging the power of Spring Boot and Spring Data JPA, the API ensures efficient and scalable operations.

## Credit

Developed by:
- Ana PALEA
- Touria SAYAGH
- Jean GUIRAUD

Thank you for using the MIAGIC Games API. We hope it meets all your event management needs.
