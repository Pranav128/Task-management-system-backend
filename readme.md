# 🚀 Task Management System - Backend (Spring Boot)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green)
![Java](https://img.shields.io/badge/Java-17-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![Hibernate](https://img.shields.io/badge/Hibernate-6.6.5-lightgrey)
![Swagger](https://img.shields.io/badge/Swagger-3.0-brightgreen)

This is the backend service for the Task Management System, built using **Spring Boot, Spring Security, Hibernate, and MySQL**. It provides a REST API for managing users, tasks, notifications, comments and attachments.

--- 

### 💻Live experience: https://taskmaster128.netlify.app
### 🛫Consume Restful web-services: https://taskmaster-v1.onrender.com/api

 <video width="100%" controls>
  <source src="Media/auth.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

<video width="100%" controls>
  <source src="Media/task.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

---
## Table of Contents

- [Features](#-features)
- [Technologies Used](#-tech-stack)
- [Setup Instructions](#-setup-instructions)
- [API Documentation](#apis)
- [Database Schema](#-database)
- [Deploying the Application](#-deployment)
- [Screenshots](#-screenshots)
- [License](#-license)
---

## **📌 Features**
- ✅ **User Authentication & JWT Authorization** (Login, Signup, Forgot Password, Role-based Access)
- ✅ **Task Management** (Create, Update, Assign, Delete, Change Status & Priority)
- ✅ **Comments & Notifications** (Users can comment & get real-time notifications)
- ✅ **File Attachments** (Upload & download files)
- ✅ **Role-based Authorization** (`ADMIN` & `USER`)
- ✅ **Pagination & Sorting** for Task Lists
- ✅ **CORS Support** (Cross-Origin Requests)

---

## **📌 Tech Stack**
- **Spring Boot 3**
- **Spring Security (JWT)**
- **Hibernate & JPA**
- **MySQL**
- **Lombok**
- **Swagger (API Documentation)**
- **Maven**
- **Docker** *(Optional)*

---

## **📌 Setup Instructions**

### Prerequisites
- Java 17
- MySQL 8.0
- Maven 3.8+

### **1️⃣ Clone the Repository**
```bash
https://github.com/Pranav128/Task-management-system-frontend.git
```

### **2️⃣ Configure MySQL Database**
```bash
Create a MySQL database named task_management_db
Update src/main/resources/application.properties
```

### **3️⃣ Run the Application**
```bash
mvn clean spring-boot:run
```
📌 Backend is now running on http://localhost:8080/api

--- 

## **📌API's**

This document outlines the available API endpoints for the application.

### **🛠Authentication**

| Method | Endpoint                    | Description                |
|--------|-----------------------------|----------------------------|
| POST   | `/api/auth/signup`          | Register a new user        |
| POST   | `/api/auth/login`           | Login & receive JWT token  |
| POST   | `/api/auth/forgot-password` | Send reset link to email   |
| POST   | `/api/auth/reset-password`  | Reset password using token |

### **📃Task Management**

| Method | Endpoint          | Description                     |
|--------|-------------------|---------------------------------|
| GET    | `/api/tasks`      | Get all tasks (with pagination) |
| POST   | `/api/tasks`      | Create a new task               |
| PUT    | `/api/tasks/{id}` | Update task details             |
| DELETE | `/api/tasks/{id}` | Delete a task                   |

### **🔔Comments & Notifications**

| Method | Endpoint                      | Description                    |
|--------|-------------------------------|--------------------------------|
| POST   | `/api/comments/{taskId}`      | Add a comment to a task        |
| GET    | `/api/notifications/{taskId}` | Fetch notifications for a task |


--- 


## **📌 Database**
**ERR Diagram**
![Home](Media/ERR%20diagram.png "ERR")
![Home](Media/entities.png "ERR")
--- 


## **📌 Deployment**

### **🛠 Build JAR**
```bash
mvn clean package -DskipTests
```

### **🚀 Run JAR**
```bash
java -jar target/task-management-system.jar
```
### **🐳 Docker Deployment**
```bash
docker build -t task-management-backend .
docker run -p 8080:8080 task-management-backend
```
--- 


## **📌 Screenshots**

1. **Home Page**
   ![Home](Media/screenshots/home1.png "Home")
2. **Signup Page**
   ![Signup](Media/screenshots/signup.png "Signup")
3. **Login Page**
   ![Login](Media/screenshots/login.png "Login")
4. **Dashboard Page**
   ![Dashboard](Media/screenshots/dashboard.png "Dashboard")
5. **Task List Page**
   ![TaskList](Media/screenshots/taks-list.png "TaskList")
6. **Task Details Page**
   ![Task Details](Media/screenshots/taskDetails1.png "Task Details")
   ![Task Details](Media/screenshots/taskDetails2.png "Task Details")
7. **New Task Page**
   ![Home](Media/screenshots/createTask1.png "New Task")
   ![Home](Media/screenshots/createTask2.png "New Task")
   ![Home](Media/screenshots/createTask3.png "New Task")
8. **Profile Page**
   ![Profile](Media/screenshots/profile1.png "Profile")
   ![Profile](Media/screenshots/profile2.png "Profile")
9. **Forgot-Password Page**
   ![Forgot-Password](Media/screenshots/forgotPass.png "Forgot-Password")
10. **Reset-Password Page**
    ![Reset-Password](Media/screenshots/resetPass.png "Reset-Password")
11. **Access-denied Page**
    ![Access-denied](Media/screenshots/access-denied.png "Access-denied")

---

## **📌 License**
**This project is licensed under the MIT License. See the [LICENSE](/LICENSE) file for details.**

--- 
