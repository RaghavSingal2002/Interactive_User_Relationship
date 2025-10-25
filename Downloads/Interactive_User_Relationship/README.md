# 🧩 User Relationship Management API

A Spring Boot RESTful API for managing users, their friendships, and relationship graph data.
It supports CRUD operations, relationship creation/removal, and dynamic popularity score computation for each user.

---

## 🚀 Features

- Create, read, update, and delete users
- Manage mutual friendships between users
- Prevent circular or duplicate friendships
- Compute popularity score for each user
- Restrict deletion of users who still have friends
- Retrieve user relationship graph
- Centralized error handling with HTTP status codes

---

## 📦 Tech Stack

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Maven**
- **IntelliJ IDEA**

---

## 🧠 Popularity Score Formula


Example:
- If a user has **3 friends** and **4 shared hobbies** with them:
  → popularityScore = 3 + (4 × 0.5) = **5.0**

---

## 🔗 API Endpoints

### 👤 **User Management**
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `GET` | `/api/users` | Fetch all users with popularity score |
| `POST` | `/api/users` | Create a new user |
| `PUT` | `/api/users/{id}` | Update user details |
| `DELETE` | `/api/users/{id}` | Delete user (only if no friends) |

### 🤝 **Relationship Management**
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/users/{id}/link?friendId={friendId}` | Create mutual friendship |
| `DELETE` | `/api/users/{id}/unlink?friendId={friendId}` | Remove friendship |

### 🧭 **Graph Data**
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `GET` | `/api/users/graph` | Returns user graph (users + relationships) |

---

## 📘 Example JSON Object

```json
{
  "id": "1b2c3d4e-5678",
  "username": "Alice",
  "age": 25,
  "hobbies": ["reading", "painting"],
  "friends": ["2f3g4h5i-6789"],
  "createdAt": "2025-10-24T12:45:10",
  "popularityScore": 4.5
}
