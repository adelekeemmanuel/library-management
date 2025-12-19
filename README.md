
# 📚 Library Management API (Spring Boot + PostgreSQL)

A simple and clean **Library Management System** built using **Java, Spring Boot, and PostgreSQL**.
This API allows you to:

* Manage books
* Manage borrowers
* Borrow and return books
* Track lending records
* View borrowing history

Everything is built with simplicity so anyone can easily set up, understand, and use the API.



# 🚀 Features

### ✅ Book Management

* Add new books
* Update book details
* Delete books
* Get a single book
* Get all books
* Search books by title, author, or ISBN

### 👤 Borrower Management

* Register borrowers
* Update borrower details
* Delete borrowers
* View single borrower
* List all borrowers

### 🔁 Lending / Borrowing

* Borrow a book
* Return a book
* View borrowing history
* View currently borrowed books



# 🏗️ Project Architecture


src/main/java
 └── com.pl.librarymanagement
      ├── controller     → Handles API endpoints
      ├── service        → Main business logic
      ├── dto            → Data Transfer Objects
      ├── entity         → JPA Entities (Books, Borrowers, Lending Records)
      └── repository     → JPA Repositories for DB access


This structure keeps the project clean, modular, and easy to extend.


# 🗂️ Database Structure

Your PostgreSQL database contains **3 tables**:

### 📘 books

| Column           | Type           | Description                |
| ---------------- | -------------- | -------------------------- |
| id               | bigserial PK   | Auto-generated             |
| title            | varchar        | Book title                 |
| author           | varchar        | Author name                |
| isbn             | varchar unique | Unique book identifier     |
| total_copies     | int            | Total copies in library    |
| available_copies | int            | Currently available copies |



### 👤 borrowers

| Column    | Type           | Description |
| --------- | -------------- | ----------- |
| id        | bigserial PK   |             |
| name      | varchar        |             |
| email     | varchar unique |             |
| member_id | varchar unique |             |



### 🔁 lending_records

| Column      | Type           | Description |
| ----------- | -------------- | ----------- |
| id          | bigserial PK   |             |
| book_id     | FK → books     |             |
| borrower_id | FK → borrowers |             |
| borrowed_at | date           |             |
| due_date    | date           |             |
| returned_at | date nullable  |             |



# 🔗 ER Diagram (Text Version)

```
Borrower (1) ----- (M) LendingRecord (M) ----- (1) Book
```

Meaning:

* A borrower can borrow many books
* A book can be borrowed many times
* Every borrowing action creates a lending record


---

# 📦 API JSON Examples

This section shows the JSON format for all major endpoints in the API.

---

# 📘 BOOK ENDPOINTS (`/api/books`)

---

## **1️⃣ Create Book — POST `/api/books`**

### ✅ Request Body (BookRequest)

```json
{
  "title": "Clean Code",
  "author": "Robert Martin",
  "isbn": "9780132350884",
  "totalCopies": 10
}
```

### ✅ Response (BookResponse)

```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert Martin",
  "isbn": "9780132350884",
  "totalCopies": 10,
  "availableCopies": 10
}
```

---

## **2️⃣ Update Book — PUT `/api/books/{id}`**

### Request Body

```json
{
  "title": "Clean Code (Updated Edition)",
  "author": "Robert Martin",
  "totalCopies": 12,
  "availableCopies": 8
}
```

---

## **3️⃣ Search Books Example**

### GET `/api/books/search/title?title=clean`

Response:

```json
[
  {
    "id": 1,
    "title": "Clean Code",
    "author": "Robert Martin",
    "isbn": "9780132350884",
    "totalCopies": 10,
    "availableCopies": 5
  }
]
```

---

# 👤 BORROWER ENDPOINTS (`/api/borrowers`)

---

## **1️⃣ Create Borrower — POST `/api/borrowers`**

### Request Body

```json
{
  "name": "John Doe",
  "email": "john.doe@gmail.com",
  "memberId": "MBR001"
}
```

### Response

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@gmail.com",
  "memberId": "MBR001"
}
```

---

## **2️⃣ Update Borrower — PUT `/api/borrowers/{id}`**

### Request Body

```json
{
  "name": "John Doe Updated",
  "email": "updated.john@gmail.com"
}
```

---

# 🔁 LENDING ENDPOINTS (`/api/lending`)

---

## **1️⃣ Borrow Book — POST `/api/lending/borrow`**

### Request Body (BorrowRequest)

```json
{
  "bookId": 1,
  "borrowerId": 3
}
```

### Response (LendingRecordResponse)

```json
{
  "id": 12,
  "bookId": 1,
  "bookTitle": "Clean Code",
  "borrowerId": 3,
  "borrowerName": "John Doe",
  "borrowedAt": "2025-01-12",
  "dueDate": "2025-01-26",
  "returnedAt": null
}
```

---

## **2️⃣ Return Book — POST `/api/lending/return`**

### Request Body (ReturnBookRequest)

```json
{
  "lendingRecordId": 12
}
```

### Response

```json
{
  "id": 12,
  "bookId": 1,
  "bookTitle": "Clean Code",
  "borrowerId": 3,
  "borrowerName": "John Doe",
  "borrowedAt": "2025-01-12",
  "dueDate": "2025-01-26",
  "returnedAt": "2025-01-18"
}
```

---

## **3️⃣ Borrower History — GET `/api/lending/history/{borrowerId}`**

### Response

```json
[
  {
    "id": 12,
    "bookTitle": "Clean Code",
    "borrowedAt": "2025-01-12",
    "dueDate": "2025-01-26",
    "returnedAt": "2025-01-18"
  },
  {
    "id": 13,
    "bookTitle": "Atomic Habits",
    "borrowedAt": "2025-02-01",
    "dueDate": "2025-02-15",
    "returnedAt": null
  }
]
```

---

## **4️⃣ Currently Borrowed — GET `/api/lending/current`**

### Response

```json
[
  {
    "id": 13,
    "bookId": 2,
    "bookTitle": "Atomic Habits",
    "borrowerId": 1,
    "borrowerName": "John Doe",
    "borrowedAt": "2025-02-01",
    "dueDate": "2025-02-15",
    "returnedAt": null
  }
]
```

---



# 🧪 Endpoints Summary

## 📘 Book Endpoints (`/api/books`)

| Method | Endpoint                 | Description      |
| ------ | ------------------------ | ---------------- |
| POST   | /api/books               | Add a book       |
| PUT    | /api/books/{id}          | Update book info |
| DELETE | /api/books/{id}          | Delete book      |
| GET    | /api/books/{id}          | Get one book     |
| GET    | /api/books               | Get all books    |
| GET    | /api/books/search/title  | Search by title  |
| GET    | /api/books/search/author | Search by author |
| GET    | /api/books/search/isbn   | Search by ISBN   |



## 👤 Borrower Endpoints (`/api/borrowers`)

| Method | Endpoint            | Description        |
| ------ | ------------------- | ------------------ |
| POST   | /api/borrowers      | Register borrower  |
| PUT    | /api/borrowers/{id} | Update borrower    |
| DELETE | /api/borrowers/{id} | Delete borrower    |
| GET    | /api/borrowers/{id} | Get borrower       |
| GET    | /api/borrowers      | List all borrowers |



## 🔁 Lending Endpoints (`/api/lending`)

| Method | Endpoint                          | Description              |
| ------ | --------------------------------- | ------------------------ |
| POST   | /api/lending/borrow               | Borrow a book            |
| POST   | /api/lending/return               | Return a book            |
| GET    | /api/lending/history/{borrowerId} | Borrower history         |
| GET    | /api/lending/current              | Currently borrowed books |



# 🔧 How the API Works (Simple Explanation)

### 1️⃣ Adding Books

When you add a book, you provide title, author, ISBN, and total copies.
The system automatically sets available copies equal to total copies.



### 2️⃣ Registering Borrowers

Borrowers must have a **name**, **email**, and **member ID**.
Emails and member IDs are unique.



### 3️⃣ Borrowing a Book

The API:

1. Checks if the book exists
2. Checks if the borrower exists
3. Confirms the book has available copies
4. Reduces available copies by 1
5. Creates a lending record with:

   * borrowed_at = today
   * due_date = today + 14 days
   * returned_at = null

---

### 4️⃣ Returning a Book

The API:

1. Retrieves the lending record
2. Marks `returned_at` as today
3. Increases book’s available copies by 1

---

### 5️⃣ Viewing Borrowing History

Shows all books a borrower ever borrowed — both returned and currently borrowed.

---

### 6️⃣ Viewing Currently Borrowed Books

Shows all lending records where `returned_at` is NULL.

---

# ⚙️ Running the Project

### Requirements

* Java 17+
* Maven
* PostgreSQL installed
* pgAdmin (optional)

### Setup

1. Create PostgreSQL database:

   ```
   CREATE DATABASE librarydb;
   ```
2. Update `application.properties`:

   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
3. Run the project:

   ```
   mvn spring-boot:run
   ```

---

# 🐳 Optional: Container Support

If you cannot run Docker, you can use **Podman**.
It works exactly like Docker.

Example:

```
podman run -d -p 5432:5432 -e POSTGRES_PASSWORD=1234 postgres
```

---

# 🤝 Contributing

If you want to improve:

* Add transaction logs
* Add email notifications
* Add admin roles
* Add fine/penalty for overdue books

PRs are welcome

---

# 📜 License

This project is free to use and modify.

---


