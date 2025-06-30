# Day 11 Task

---

### 23IOTA10

Designed a Java-based Event Ticket Token Sy
stem using MongoDB, Vert.x and Postman

- Users register with email and name ✅

- A random password is generated and emailed to the user via SMTP ✅

- users log in using email and password ✅

- user can view a list of events ✅

- users can book a token for a specific event ✅

- After booking, a unique token is generated and emailed to the user ✅

- The events seat count is reduced by one ✅

### Technologies Used

- Java 17

- Vert.x 

- MongoDB Compass (mongodb://localhost:27017)

- Postman (for API testing)

- SMTP (for email sending via Gmail)

### Project Structure

```
src
└── main
    └── java
        └── in
            └── edu
                └── kristujayanti
                    ├── Main.java              
                    ├── MongoConnection.java  
                    ├── handlers
                    │   ├── AuthHandler.java   
                    │   ├── EventHandler.java  
                    │   └── BookingHandler.java
                    └── services
                        ├── AuthService.java   
                        ├── EventService.java  
                        ├── BookingService.java
                        └── MailService.java       
     
```

---

## screenshots

- ### **1: Registration**
![user_reg(sucess).png](screenshots/user_reg%28sucess%29.png)

- **failed registration**
![user_reg(fail).png](screenshots/user_reg%28fail%29.png)

- **password received**
![password_recieved.png](screenshots/password_recieved.png)

- ### **2: login**
![login(sucess).png](screenshots/login%28sucess%29.png)

- **failed login**
![failed_login.png](screenshots/failed_login.png)


- ### **3: Events**
![mongodb_events(beforefill).png](screenshots/mongodb_events%28beforefill%29.png)
![list_all_events.png](screenshots/list_all_events.png)


- ### **4: Event Booking**
![booking_success.png](screenshots/booking_success.png)

- **booking success email**
![booking_success_tokenemail.png](screenshots/booking_success_tokenemail.png)

- **failed booking**
![booking_failed.png](screenshots/booking_failed.png)

- ### **5: Events after booking**

- **MongoDB events after booking**
![reduced_seats(mongo).png](screenshots/reduced_seats%28mongo%29.png)

- **Events after booking**
![events_after_reg.png](screenshots/events_after_reg.png)


--- 
## Methods explaination :-
| File                  | Description                                  |
|-----------------------|----------------------------------------------|
| `Main.java`           | Starts the server and sets up routes         |
| `MongoConnection.java`| Connects to MongoDB                          |
| `AuthHandler.java`    | Handles user registration and login routes   |
| `EventHandler.java`   | Handles event listing route                  |
| `BookingHandler.java` | Handles event booking route                  |
| `AuthService.java`    | Logic for user registration and login        |
| `EventService.java`   | Logic to manage events                       |
| `BookingService.java` | Logic to book events and generate tokens     |
| `MailService.java`    | Sends emails using Vert.x MailClient         |

---

## HTTP endpoints :-
| Method | Endpoint         | Description                | Body (JSON) Example                                      |
|--------|------------------|----------------------------|----------------------------------------------------------|
| POST   | /api/register    | Register a new user        | { "name": "abc", "email": "abc@example.com" }            |
| POST   | /api/login       | Login with email and password | { "email": "john@example.com", "password": "abcd1234" }  |
| POST   | /api/events/book | Book a token for an event  | { "email": "doe@example.com", "eventId": "EVT001" }      |
| GET    | /api/events      | Get list of all events     |                                                          |

**Steps to Generate a Google App Password**

1. **Login** to your Google Account
2. Go to the Security tab.
3. find and click **App Passwords** under "Signing in to Google". .
4. For "Select device"and enter a name.
5. Click generate.
6. Copy the 16-character password shown use it in the project.

This project is a educational task given to **23IOTA10**