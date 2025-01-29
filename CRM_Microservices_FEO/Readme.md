
# Front-End Office (FEO) Service

## Overview

The Front-End Office (FEO) Service is a Spring Boot-based application that provides functionality to manage customer requests. It interacts with a Back-End Office service to notify updates, cancellations, and status changes for requests. This service allows for the creation, retrieval, cancellation, and status updates of customer requests.

## Features

- **Create Customer Requests**: Enables the creation of customer requests with an initial "PENDING" status.
- **Retrieve Customer Requests**: Supports fetching a specific customer request by ID or retrieving all customer requests.
- **Cancel Customer Requests**: Allows for canceling a customer request and notifying the Back-End Office service.
- **Update Request Status**: Allows updating the status of a customer request (e.g., PENDING, IN_PROGRESS, COMPLETED, CANCELED).
- **Integration with CRM**: The service integrates with the Back-End Office through REST API calls to notify about request creation, cancellation, and status updates.

## Application Configuration

- **Application Name**: `CRM_FEO`
- **Port**: `8081`
- **Database**: SQL Server (JDBC)
- **DataSource Configuration**:
    - URL: `jdbc:sqlserver://localhost:1433;databaseName=master;encrypt=true;trustServerCertificate=true;`
    - Username: `sa`
    - Password: `YourStrongPassword123`
    - Driver: `com.microsoft.sqlserver.jdbc.SQLServerDriver`

## Dependencies

- Spring Boot 2.x
- Spring Data JPA
- Spring Web
- Lombok
- Jakarta Persistence
- SLF4J Logger
- RestTemplate for HTTP requests

## Database Entity

### `CustomerRequest`

The `CustomerRequest` entity is used to represent a customer's request in the system.

- **Fields**:
    - `id`: Long (Primary Key)
    - `customerName`: String
    - `issueDetails`: String
    - `status`: RequestStatus (Enum: PENDING, IN_PROGRESS, COMPLETED, CANCELED)

### `RequestStatus`

Enum used to represent the possible statuses of a customer request:
- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELED`

### `CustomerRequestBEDTO`

DTO used to represent the data sent to the Back-End Office.

- **Fields**:
    - `crmRequestId`: Long (Links to CRM request ID)
    - `assignedTo`: String
    - `resolutionDetails`: String
    - `status`: RequestStatus (Enum: PENDING, IN_PROGRESS, COMPLETED, CANCELED)

## API Endpoints

### 1. **Create a Customer Request**
- **Endpoint**: `POST /api/customer-requests`
- **Description**: Creates a new customer request with status `PENDING`.
- **Request Body**:
  ```json
  {
    "customerName": "John Doe",
    "issueDetails": "Problem with the product"
  }
  ```
- **Response**:
    - Status: `200 OK`
    - Body:
  ```json
  {
    "id": 1,
    "customerName": "John Doe",
    "issueDetails": "Problem with the product",
    "status": "PENDING"
  }
  ```

### 2. **Get All or Specific Customer Requests**
- **Endpoint**: `GET /api/customer-requests`
- **Description**: Retrieves all customer requests or a specific request by ID.
- **Query Parameters**:
    - `id`: (optional) The ID of the customer request to fetch.
- **Response**:
    - If `id` is provided:
        - Status: `200 OK`
        - Body:
      ```json
      {
        "id": 1,
        "customerName": "John Doe",
        "issueDetails": "Problem with the product",
        "status": "PENDING"
      }
      ```
    - If no `id` is provided:
        - Status: `200 OK`
        - Body:
      ```json
      [
        {
          "id": 1,
          "customerName": "John Doe",
          "issueDetails": "Problem with the product",
          "status": "PENDING"
        },
        {
          "id": 2,
          "customerName": "Jane Smith",
          "issueDetails": "Inquiry about service",
          "status": "IN_PROGRESS"
        }
      ]
      ```

### 3. **Cancel a Customer Request**
- **Endpoint**: `PUT /api/customer-requests/{id}/cancel`
- **Description**: Cancels a customer request and notifies the Back-End Office.
- **Headers**:
    - `Header-Identify`: `"feo"` (to identify the FEO service)
- **Response**:
    - Status: `200 OK`
    - Body:
  ```json
  {
    "id": 1,
    "customerName": "John Doe",
    "issueDetails": "Problem with the product",
    "status": "CANCELED"
  }
  ```

### 4. **Update the Status of a Customer Request**
- **Endpoint**: `PUT /api/customer-requests/{id}/status`
- **Description**: Updates the status of a customer request.
- **Query Parameter**:
    - `status`: The new status of the request (e.g., `IN_PROGRESS`, `COMPLETED`, `CANCELED`).
- **Response**:
    - Status: `200 OK`
    - Body:
  ```json
  {
    "id": 1,
    "customerName": "John Doe",
    "issueDetails": "Problem with the product",
    "status": "IN_PROGRESS"
  }
  ```
  
## For Postman Collection

```Json
{
	"info": {
		"_postman_id": "f565a2c8-decb-42d9-90b3-917ac8fedb23",
		"name": "Front-End Office",
		"description": "Collection for testing CustomerRequestController API",
		"schema": "https://schema.getpostman.com/json/collection/v2.0.0/collection.json",
		"_exporter_id": "9786851"
	},
	"item": [
		{
			"name": "Get All Requests",
			"request": {
				"method": "GET",
				"header": [],
				"url": "localhost:8081/api/customer-requests"
			},
			"response": []
		},
		{
			"name": "Create New Request",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n    \"customerName\": \"Charlie\",\n    \"issueDetails\": \"Issue 3\"\n}"
				},
				"url": "localhost:8081/api/customer-requests"
			},
			"response": []
		},
		{
			"name": "Get Single Request",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8081/api/customer-requests?id={{requestId}}",
					"host": [
						"localhost"
					],
					"port": "8081",
					"path": [
						"api",
						"customer-requests"
					],
					"query": [
						{
							"key": "id",
							"value": "{{requestId}}"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Cancel Request",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					},
					{
						"key": "Header-Identify",
						"value": "feo"
					}
				],
				"url": "localhost:8081/api/customer-requests/{{requestId}}/cancel"
			},
			"response": []
		}
	]
}
```

## Error Handling

- **RequestNotFoundException**: Thrown when a customer request is not found by ID.
- **CRMNotificationException**: Thrown when a notification to the Back-End Office fails.

## Logging

The application uses SLF4J for logging. Relevant logs are provided for each operation, including successful notifications and error handling for request failures.

---

# Unit Test Documentation

## Overview
This document describes the unit testing strategy for the **CRM BEO Backend Service**. The tests validate the functionality of the service layer and controllers using **JUnit 5**, **Mockito**, and **Spring Boot Test**.

## Technologies Used for Testing
- **JUnit 5** (Unit Testing Framework)
- **Mockito** (Mocking Dependencies)
- **Spring Boot Test** (Integration Testing)
- **MockMvc** (Testing Controllers)

## Unit Test Coverage

### 1. **CustomerRequestServiceTest**
#### **Test Cases:**
- ✅ `receiveRequest_ShouldSaveRequestWithPendingStatus()`  
  **Description:** Ensures a request is saved with `PENDING` status when received.

- ✅ `updateStatus_ShouldUpdateRequestStatusSuccessfully()`  
  **Description:** Verifies that the status of an existing request is updated correctly.

- ✅ `updateStatus_ShouldThrowExceptionWhenRequestNotFound()`  
  **Description:** Ensures an exception is thrown if the request ID does not exist.

#### **Mocked Dependencies:**
- `CustomerRequestRepository` (Mocked Database)
- `CRMNotifierService` (Mocked External API Calls)

---

### 2. **CustomerRequestControllerTest**
#### **Test Cases:**
- ✅ `receiveRequest_ShouldReturnCreatedRequest()`  
  **Description:** Ensures that receiving a request returns the correct response.

- ✅ `getRequest_ShouldReturnRequestById()`  
  **Description:** Retrieves a request by its ID and returns the correct details.

- ✅ `processRequest_ShouldUpdateStatusToInProgress()`  
  **Description:** Verifies that processing a request updates its status correctly.

- ✅ `completeRequest_ShouldUpdateStatusToCompleted()`  
  **Description:** Ensures that a request marked as `COMPLETED` includes resolution details.

- ✅ `cancelRequest_ShouldUpdateStatusToCanceled()`  
  **Description:** Ensures that a request marked as `CANCELED` includes cancellation details.

#### **Testing Approach:**
- Uses `MockMvc` to simulate HTTP requests and responses.
- Mocks service layer to test only controller behavior.

---

### 3. **CRMNotifierServiceTest**
#### **Test Cases:**
- ✅ `notifyCRM_ShouldSendPutRequestToCRMService()`  
  **Description:** Ensures the correct `PUT` request is sent to the external CRM system.

- ✅ `notifyCancel_ShouldSendPutRequestToCancelRequest()`  
  **Description:** Ensures the correct `PUT` request is sent when canceling a request.

#### **Mocked Dependencies:**
- `RestTemplate` (Mocked HTTP Calls)

---

## Example Unit Test (CustomerRequestServiceTest.java)
```java
@ExtendWith(MockitoExtension.class)
class CustomerRequestServiceTest {

    @Mock
    private CustomerRequestRepository repository;

    @Mock
    private CRMNotifierService crmNotifierService;

    @InjectMocks
    private CustomerRequestService service;

    @Test
    void receiveRequest_ShouldSaveRequestWithPendingStatus() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setCrmRequestId(1L);

        when(repository.save(any(CustomerRequest.class))).thenReturn(request);

        // Act
        CustomerRequest savedRequest = service.receiveRequest(request);

        // Assert
        assertNotNull(savedRequest);
        assertEquals(RequestStatus.PENDING, savedRequest.getStatus());
        verify(repository, times(1)).save(any(CustomerRequest.class));
    }
}
```

## Running Unit Tests
Run all unit tests using:
```sh
mvn test
```

For specific tests, use:
```sh
mvn -Dtest=CustomerRequestServiceTest test
```

---

## Summary
- **Unit tests cover service logic and controller behavior.**
- **Mockito is used to isolate dependencies.**
- **Spring Boot Test ensures proper integration testing.**

🚀 Happy Testing!

---