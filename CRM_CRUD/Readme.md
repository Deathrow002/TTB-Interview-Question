# CRM CRUD Application Overview

## Introduction
The CRM (Customer Relationship Management) CRUD Application is a Spring Boot-based system designed to manage customer and sales data. The application allows users to perform Create, Read, Update, and Delete (CRUD) operations for both customers and sales. It integrates with a relational database (SQL Server) to persist customer information, track sales transactions, and generate reports.

## Key Features
1. **Customer Management**: CRUD operations for customers, including attributes like first name, last name, VIP status, and customer creation date.
2. **Sales Management**: Track sales for each customer, including sales amount, date, and transaction timestamps.
3. **Sales Ranking**: Retrieve top customers based on sales, along with their rank.
4. **Data Generation**: Generate sample customer and sales data for testing and development purposes.

---

### Components of the Application

#### 1. Application Setup

- **Main Class (`CRM_CRUD_Application`)**:
  The entry point for the Spring Boot application. The `@SpringBootApplication` annotation initializes the Spring Boot framework, and the application is run using the `SpringApplication.run()` method.

---

#### 2. Entities

- **Customer Entity** (`Customer`):
    - **Attributes**:
        - `customerId`: UUID (Unique identifier for the customer).
        - `firstName`: Customer's first name (required).
        - `lastName`: Customer's last name (required).
        - `customerDate`: Date when the customer was created (required).
        - `isVIP`: Boolean indicating if the customer is a VIP (required).
        - `statusCode`: Customer status code (max length: 10).
        - `createdOn`: Timestamp of when the customer was created (auto-generated).
        - `modifiedOn`: Timestamp of when the customer was last updated (auto-generated).
    - **Lifecycle Methods**:
        - `@PrePersist`: Automatically generates a unique `customerId` before persisting a new customer entity.

- **Sale Entity** (`Sale`):
    - **Attributes**:
        - `saleId`: UUID (Unique identifier for the sale).
        - `customerId`: UUID (Foreign key linking to the `Customer` entity).
        - `saleAmount`: Sale amount (required).
        - `saleDate`: Date when the sale occurred (required).
        - `createdAt`: Timestamp when the sale was created (auto-generated).
        - `updatedAt`: Timestamp when the sale was last updated (auto-generated).
    - **Lifecycle Methods**:
        - `@PrePersist`: Automatically generates a unique `saleId` before persisting a new sale entity.

---

#### 3. Data Transfer Objects (DTOs)

- **CustomerDTO**:
    - Used for transferring customer data between layers (e.g., from the controller to the service).
    - Includes fields for `customerId`, `firstName`, `lastName`, `customerDate`, `isVIP`, `statusCode`, `createdOn`, and `modifiedOn`.

- **CustomerSalesDTO**:
    - Represents aggregated sales data for a customer, including `customerId`, `totalSales`, and `rank` (ranking customers based on their total sales).
    - This DTO is used to present data in reports or API responses.

- **SaleDTO**:
    - Used for transferring sale data between layers.
    - Includes fields for `customerId`, `saleAmount`, `saleDate`, `createdAt`, and `updatedAt`.

---

#### 4. Repositories

- **CustomerRepository**:
    - Extends `JpaRepository` to provide standard CRUD operations for the `Customer` entity.
    - Enables database interactions such as saving, updating, deleting, and querying customers.

- **SaleRepository**:
    - Extends `JpaRepository` and includes a custom query (`findTopSalesRanks`) to retrieve aggregated sales data for customers.
    - The custom query ranks customers based on their total sales.

---

#### 5. Services

- **CustomerService**:
    - Provides business logic for adding and updating customers.
    - Includes methods for retrieving a customer by ID (`getCustomerById`) and converting DTOs to entities.

- **SaleService**:
    - Provides business logic for retrieving aggregated sales data for customers.
    - Uses the custom query in `SaleRepository` to rank customers based on total sales in the past year (`getAllSalesByCustomerId`).

---

#### 6. Controller

- **SaleController**:
    - Exposes a RESTful API endpoint (`/api/sales/top-sales`) to retrieve the top sales for each customer.
    - The endpoint returns a list of `CustomerSalesDTO` objects, including customer ID, total sales, and rank.

---

#### 7. Data Generator

- **DataGeneratorApplication**:
    - Contains a `CommandLineRunner` bean to generate 1,000 sample customers and sales records.
    - Randomized customer data (e.g., first name, last name, VIP status) and sales data (e.g., sale amount, sale date) are generated and persisted to the database for testing purposes.

---

### Application Configuration

- **`application.properties`**:
    - Configures the application to run on port 8080.
    - Configures the connection to a SQL Server database (`spring.datasource.url`, `spring.datasource.username`, etc.).
    - Configures Hibernate to update the database schema (`spring.jpa.hibernate.ddl-auto=update`).

---

### API Endpoints

1. **GET /api/sales/top-sales**:
    - **Description**: Retrieves the top sales for each customer.
    - **Response**: A list of `CustomerSalesDTO` objects containing `customerId`, `totalSales`, and `rank`.

---

### For Postman Collection
```Json
{
	"info": {
		"_postman_id": "6ca6cba0-51ac-4d6b-a00e-be89e31a110e",
		"name": "CRM_CRUD",
		"schema": "https://schema.getpostman.com/json/collection/v2.0.0/collection.json",
		"_exporter_id": "9786851"
	},
	"item": [
		{
			"name": "top-sales",
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"method": "GET",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": "http://localhost:8080/api/sales/top-sales"
			},
			"response": []
		}
	]
}
```

---
