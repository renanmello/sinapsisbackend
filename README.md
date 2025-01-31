🖥️ Back-end Sinapsis

### Features/stacks ###

Java with Spring Boot

Spring Security for authentication and authorization

Spring Data JPA for persistence

MySQL as a database

JUnit 5 and Mockito for unit testing

Javadoc generation for automatic code documentation

📌 Back-end Installation and Execution

Clone the repository:
```
git clone https://github.com/seu-usuario/spring-backend.git
```
Navigate to the folder:
```
cd spring-backend
```
Create and Configure the database in the .env file:
```
DATABASE_NAME="Your_database_name"
DATABASE_USERNAME="Your_user_name"
DATABASE_PASSWORD="Your_Password"
```
Configure the database in application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/${DATABASE_NAME}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
```
Compile and run the project using Maven:
```
mvn clean install
mvn spring-boot:run
```
The API will be running on:
```
http://localhost:8080
```
🔒 Security and Testing

The backend relies on Spring Security for authentication and access control.

Unit tests were implemented using JUnit 5 and Mockito for services and repositories.

Contact

If you have any questions or suggestions, feel free to open an issue or get in touch!

🚀 Developed by Renan Pereira Mello.

