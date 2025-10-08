# Blog Platform (Spring Boot + PostgreSQL + JWT)

A simple Blog Platform REST API built with **Spring Boot**, **Spring Web**, **Spring Data JPA**, **Spring Security**, **Spring Test**, **Java JWT**, and **PostgreSQL**.

Supports CRUD operations for posts and comments. Implements user login via JWT.

---

## Setup

### 1. Clone and Build
```bash
git clone https://github.com/banaga-benjamin/blogplatform
cd blogplatform

mvn clean install
```

### 2. Configure PostGreSQL
Login to PostgreSQL and create the database and user:
```sql
CREATE DATABASE postdb;
CREATE USER postuser WITH PASSWORD 'postpass';
GRANT ALL PRIVILEGES ON DATABASE postdb TO postuser;

\c postdb;
GRANT ALL PRIVILEGES ON SCHEMA public TO postuser;
```

### 3. Application Properties
The application is configured with the following defaults in
`src/main/resources/application.properties`:
```
# postgres settings
spring.datasource.username=postuser
spring.datasource.password=postpass
spring.datasource.url=jdbc:postgresql://localhost:5432/postdb

# jpa settings
spring.jpa.hibernate.ddl-auto=update
```

### 4. Run the Application
```
mvn spring-boot:run
```
---

## Testing

### 1, Configure PostGreSQL
Configure PostGreSQL
```sql
CREATE DATABASE postdb_test;
CREATE USER postuser WITH PASSWORD 'postpass';
GRANT ALL PRIVILEGES ON DATABASE postdb_test TO postuser;

\c postdb_test;
GRANT ALL PRIVILEGES ON SCHEMA public TO postuser;
```

### 2. Application Properties
For testing, the application is configured with the following defaults in
`src/test/resources/application.properties`:
```
spring.application.name=blogplatform

# postgres settings
spring.datasource.username=postuser
spring.datasource.password=postpass
spring.datasource.url=jdbc:postgresql://localhost:5432/postdb_test

# jpa settings
spring.jpa.hibernate.ddl-auto=create-drop

```

### 3. Run Tests

```
mvn test
```

---

## API Documentation

### Base URL and Login

The base url for accessing posts is:
```
http://localhost:8080/apis/post
```

The base url for accessing comments is:
```
http://localhost:8080/apis/comment
```


For the register and login endpoints, the base url is:
```
http://localhost:8080/apis/auth
```

### Register & Login Endpoints (endpoint and request body)
1. Register:
- POST `/apis/auth/register`
- Request Body:
    ```json
    {
        "username": "username",
        "password": "password"
    }
    ```

2. Login:
- POST `/apis/auth/login`
- Request Body:
    ```json
    {
        "username": "username",
        "password": "password"
    }
    ```

### JWT Authentication

The APIs use JWT for authentication. When accessing the post and comment endpoints, a valid token must be present as the value of the Authorization header of the request body. 

```json
    {
        "Authorization": "Bearer [valid-token]",
    }
```

A token is returned as a response upon valid login.