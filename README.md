# URL Shortener

URL shortener converts long URLs into shorter ones to save
space when sharing URLs in messages, twitter, presentations
etc. When user opens the shortened URL, it will be
automatically redirected to original (long) URL.

# Used Technologies

- Java 17
- Spring Boot
- MongoDB
- Keycloak
- JPA/Hibernate
- Maven 
- Docker

# Installation

### **Backend**

Navigate to the project directory and compile the project using Maven:
```sh
mvn clean install
```  
Configure or run a container for MongoDB using Docker Compose:
```sh
Default configuration

spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=rootuser
spring.data.mongodb.password=rootpass
spring.data.mongodb.database=urlDatabase
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
``` 
```sh
or docker-compose up
```  
Run the application:
```sh
mvn spring-boot:run
```  
The application should now be running at http://localhost:8082

