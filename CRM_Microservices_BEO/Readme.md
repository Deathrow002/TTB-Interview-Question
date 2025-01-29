# CRM BEO Backend Service

## Overview
The **CRM BEO Backend Service** is a Spring Boot application that manages customer service requests for a back-office system. It provides APIs for receiving, updating, retrieving, and canceling customer requests while ensuring seamless communication with external CRM services.

## Technologies Used
- **Spring Boot** (Spring Web, Spring Data JPA)
- **HikariCP** (Connection Pooling)
- **SQL Server** (Database)
- **Lombok** (Reducing Boilerplate Code)
- **Jakarta Persistence API (JPA)** (ORM)
- **RestTemplate** (External API Calls)
- **SLF4J / Logger** (Logging)

## API Endpoints

### 1. Receive a New Customer Request
**Endpoint:**  
`POST /api/backoffice/receive`

**Request Body (JSON):**
```json
{
  "crmRequestId": 12345,
  "assignedTo": "John Doe",
  "resolutionDetails": "Issue reported",
  "status": "PENDING"
}
```
**Response:** Returns the created customer request.

---

### 2. Retrieve a Customer Request by ID
**Endpoint:**  
`GET /api/backoffice/{crmRequestId}`

**Response:** Returns the requested customer record.

---

### 3. Retrieve All Customer Requests
**Endpoint:**  
`GET /api/backoffice`

**Response:** Returns a list of all customer requests.

---

### 4. Process a Customer Request
**Endpoint:**  
`POST /api/backoffice/{crmRequestId}/process`

**Request Parameters:**
- `assignedTo` (String) - The name of the assigned agent.
- `status` (Enum: `IN_PROGRESS`)

**Request Header:**
- `Header-Identify: beo`

**Response:** Updates the request and assigns an agent.

---

### 5. Complete a Customer Request
**Endpoint:**  
`POST /api/backoffice/{crmRequestId}/complete`

**Request Parameters:**
- `resolutionDetails` (String) - The resolution details.
- `status` (Enum: `COMPLETED`)

**Request Header:**
- `Header-Identify: beo`

**Response:** Updates the request with resolution details.

---

### 6. Cancel a Customer Request
**Endpoint:**  
`POST /api/backoffice/{crmRequestId}/canceled`

**Request Parameters:**
- `resolutionDetails` (String) - The reason for cancellation.
- `status` (Enum: `CANCELED`)

**Request Header:**
- `Header-Identify: beo`

**Response:** Cancels the request and updates the status.

---

## Database Configuration (application.properties)
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=master;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=YourStrongPassword123
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.hikari.maximum-pool-size=10
spring.jpa.hibernate.ddl-auto=update
```

## External CRM Notification
- Notifies the external CRM system when a request status changes.
- Uses `RestTemplate` to send `PUT` requests to `http://localhost:8081/api/customer-requests/{crmRequestId}/status`

---

## Exception Handling
- Throws `RequestNotFoundException` if a request ID is not found.
- Throws `IllegalArgumentException` if an invalid status update occurs.

---
## For Postman Collection
```Json
{
	"info": {
		"_postman_id": "88caa8e1-6b1a-4711-80cd-0e59c37abfcb",
		"name": "Back-End Office",
		"description": "Postman Collection for Customer Request API in Backoffice",
		"schema": "https://schema.getpostman.com/json/collection/v2.0.0/collection.json",
		"_exporter_id": "9786851"
	},
	"item": [
		{
			"name": "Receive Customer Request",
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
					"raw": "{\"assignedTo\": \"John Doe\", \"resolutionDetails\": \"Details of the request.\"}"
				},
				"url": "localhost:8082/api/backoffice/receive"
			},
			"response": []
		},
		{
			"name": "Get Customer Request by ID",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "localhost:8082/api/backoffice/:crmRequestId",
					"host": [
						"localhost"
					],
					"port": "8082",
					"path": [
						"api",
						"backoffice",
						":crmRequestId"
					],
					"variable": [
						{
							"key": "crmRequestId",
							"value": "1"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Get All Customer Requests",
			"request": {
				"method": "GET",
				"header": [],
				"url": "localhost:8082/api/backoffice"
			},
			"response": []
		},
		{
			"name": "Process Customer Request",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Header-Identify",
						"value": "beo"
					},
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "assignedTo",
							"value": "John Doe",
							"type": "text"
						},
						{
							"key": "status",
							"value": "IN_PROGRESS",
							"type": "text"
						}
					]
				},
				"url": {
					"raw": "localhost:8082/api/backoffice/:crmRequestId/process",
					"host": [
						"localhost"
					],
					"port": "8082",
					"path": [
						"api",
						"backoffice",
						":crmRequestId",
						"process"
					],
					"variable": [
						{
							"key": "crmRequestId",
							"value": "20"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Complete Customer Request",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Header-Identify",
						"value": "beo"
					},
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "resolutionDetails",
							"value": "Completed the request.",
							"type": "text"
						},
						{
							"key": "status",
							"value": "COMPLETED",
							"type": "text"
						}
					]
				},
				"url": {
					"raw": "localhost:8082/api/backoffice/:crmRequestId/complete",
					"host": [
						"localhost"
					],
					"port": "8082",
					"path": [
						"api",
						"backoffice",
						":crmRequestId",
						"complete"
					],
					"variable": [
						{
							"key": "crmRequestId",
							"value": "1"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "Cancel Customer Request",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Header-Identify",
						"value": "beo"
					},
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "resolutionDetails",
							"value": "Request canceled.",
							"type": "text"
						},
						{
							"key": "status",
							"value": "CANCELED",
							"type": "text"
						}
					]
				},
				"url": {
					"raw": "localhost:8082/api/backoffice/:crmRequestId/canceled",
					"host": [
						"localhost"
					],
					"port": "8082",
					"path": [
						"api",
						"backoffice",
						":crmRequestId",
						"canceled"
					],
					"variable": [
						{
							"key": "crmRequestId",
							"value": "1"
						}
					]
				}
			},
			"response": []
		}
	]
}
```

---

## Running the Application
1. Ensure SQL Server is running by running `docker-compose` It will create MS-SQL Database.
   ```sh
    docker-compose up -d
      ```
[//]: # (Update database credentials in `application.properties`.)
2. Run the application:
   ```sh
    mvn spring-boot:run
   ```
3. The service will be available on `http://localhost:8082`.

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