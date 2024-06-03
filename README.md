# API Documentation

## This is a small and simple API for a library bookstore using Spring Web and Java. In the resources file, you can locate a 'part1.yaml' file, it is a Swagger File for the API documentation.



## API Information

- **API Version:** 1.0.0
- **Base URL:** `http://localhost:8080`

## Endpoints

### Authors

Manage operations related to authors.

- **GET /authors**
  - **Summary:** Retrieve a list of all authors.
  - **Response:** 200 (Successful operation), 404 (Invalid input)

- **POST /authors**
  - **Summary:** Create a new author.
  - **Response:** 201 (Author created successfully), 400 (Invalid input)

- **GET /authors/{id}**
  - **Summary:** Retrieve an author by their ID.
  - **Response:** 200 (Successful operation), 404 (Author not found)

- **PUT /authors/{id}**
  - **Summary:** Update an existing author.
  - **Response:** 200 (Successful operation), 404 (Author not found)

- **DELETE /authors/{id}**
  - **Summary:** Delete an author by their ID.
  - **Response:** 400 (Invalid ID provided), 404 (Author not found)

- **GET /authors/{id}/books**
  - **Summary:** Retrieve all books written by a specific author.
  - **Response:** 204 (Successful operation)

### Books

Manage operations related to books.

- **GET /books**
  - **Summary:** Retrieve a list of all books.
  - **Response:** 200 (Successful operation)

- **POST /books**
  - **Summary:** Create a new book.
  - **Response:** 201 (Book created successfully), 400 (Invalid input)

- **GET /books/{ISBN}**
  - **Summary:** Retrieve a book by its ISBN.
  - **Response:** 204 (Successful operation)

- **PUT /books/{ISBN}**
  - **Summary:** Update an existing book.
  - **Response:** Successful operation

- **DELETE /books/{ISBN}**
  - **Summary:** Delete a book by its ISBN.
  - **Response:** 400 (Invalid ISBN supplied), 404 (Book not found)

- **GET /books/{ISBN}/authors**
  - **Summary:** List all authors of a specific book.
  - **Response:** 204 (No content)

- **GET /books/{ISBN}/orders**
  - **Summary:** Retrieve all orders containing a specific book.
  - **Response:** 200 (Successful operation), 404 (Book not found or has no orders)

### Orders

Manage operations related to orders.

- **GET /orders**
  - **Summary:** List all orders.
  - **Response:** 200 (Successful operation), 404 (Not found)

- **POST /orders**
  - **Summary:** Create a new order.
  - **Response:** 201 (Successful operation), 400 (Bad request)

- **GET /orders/{id}**
  - **Summary:** Retrieve an order by its ID.
  - **Response:** 200 (Successful operation), 404 (Order not found)

- **PUT /orders/{id}**
  - **Summary:** Update an existing order.
  - **Response:** 200 (Successful operation), 404 (Not found)

- **GET /orders/{id}/books**
  - **Summary:** Retrieve all books in an order.
  - **Response:** 200 (Successful operation), 204 (No content)

- **POST /orders/{id}/books**
  - **Summary:** Add a book to an existing order.
  - **Response:** 201 (Successful operation), 400 (Bad request)

- **DELETE /orders/{id}/books/{ISBN}**
  - **Summary:** Remove a book from an existing order.
  - **Response:** 204 (No content), 404 (Not found)

## Schemas

### Author

- **id:** integer (example: 10)
- **name:** string (example: George Orwell)
- **birthyear:** integer (example: 1903)
- **nationality:** string (example: English)

### Book

- **ISBN:** string (example: 978-3-16-148410-0)
- **title:** string (example: 1984)
- **publicationYear:** integer (example: 1949)
- **price:** number (example: 10.99)

### Order

- **id:** integer (example: 16)
- **datetime:** string (format: date-time)
- **customerName:** string (example: John Doe)

## Tags

- **authors:** Operations about authors
- **books:** Operations about books
- **orders:** Operations about orders

---

This documentation provides an overview of the available API endpoints and their functionalities for managing authors, books, and orders in the Bookstore application. For more detailed information, refer to the individual endpoint descriptions and response schemas.
