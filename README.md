# SpringBootBloggingAPIRESTFUL

A RESTful API backend that serves as a blogging application built using the Spring Framework.

## Entity Relationship diagram
https://drive.google.com/file/d/1wlxnqsyFdKdSRxiMXtD65uDYqUnUXTaf/view?usp=sharing


---

## Stack

- **Spring REST**
- **Spring Boot**
- **Spring Data JPA**
- **Pagination and Sorting**
- **Spring Security**
- **Java Core**

---

## Features

1. **JWT Authentication** for Login/Logout.
2. CRUD (Create, Read, Update, Delete) operations on blogs.
3. Commenting functionality on blogs.
4. Retrieve previous blogs/posts in pages using **Pagination and Sorting**.
5. **Admin User**:
   - Allowed to delete comments.
   - Allowed to delete previous posts.
6. Deployed and documented using **Swagger UI**.
7. Simple and extensible logic for building additional API applications.

---

## How to Run

### Prerequisites

- Install an IDE with Spring support (e.g., **Spring Tool Suite** or **IntelliJ IDEA**).
- Ensure you have **MySQL** installed and running locally.

### Steps

1. **Download the Project**:
   - Clone the repository or download the ZIP file.

2. **Configure the Database**:
   - Update the `application.properties` file with your local MySQL database credentials.

   Example configuration:
   ```properties
   spring.datasource.url=jdbc:mysql://blogdb.cwzdw1eu3x8n.ap-south-1.rds.amazonaws.com:3306/blog_app_apis
   spring.datasource.username=admin
   spring.datasource.password=PlusOne97!
   ```

3. **Run the Application**:
   - Open the project in your IDE.
   - Run the `SpringBootBloggingAPIRESTFUL` application.

4. **Access the API**:
   - Use **Swagger UI** for API documentation and testing.

---

## API Documentation

The API is documented and can be accessed via **Swagger UI**. Once the application is running, navigate to:

```
http://localhost:8080/swagger-ui/
```

---

## Screenshots

### Blog Dashboard
![blogpic3](https://github.com/TechnoDiktator/SpringBootBloggingAPIRESTFUL/assets/99278069/4ade1393-c12c-4c68-b1bb-e111b9ec0ee4)

### Blog Post View
![blogpic1](https://github.com/TechnoDiktator/SpringBootBloggingAPIRESTFUL/assets/99278069/bb85cf56-df3f-4f69-b598-d70049b9b382)

### Comment Section
![blogpic2](https://github.com/TechnoDiktator/SpringBootBloggingAPIRESTFUL/assets/99278069/c6ef7f83-31f0-451d-b28e-54858c81f3d5)

---

## Future Enhancements

- Add user roles and permissions for more granular access control.
- Implement email notifications for blog updates and comments.
- Add support for file uploads (e.g., images for blogs).
- Enhance the UI for better user experience.

---

