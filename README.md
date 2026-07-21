# ReliaQuest's Entry-Level Java Challenge

Please keep the following in mind while working on this challenge:
* Code implementations will not be graded for **correctness** but rather on practicality
* Articulate clear and concise design methodologies, if necessary
* Use clean coding etiquette
  * E.g. avoid liberal use of new-lines, odd variable and method names, random indentation, etc...
* Test cases are not required

## Problem Statement

Your employer has recently purchased a license to top-tier SaaS platform, Employees-R-US, to off-load all employee management responsibilities.
Unfortunately, your company's product has an existing employee management solution that is tightly coupled to other services and therefore 
cannot be replaced whole-cloth. Product and Development leads in your department have decided it would be best to interface
the existing employee management solution with the commercial offering from Employees-R-US for the time being until all employees can be
migrated to the new SaaS platform.

Your ask is to expose employee information as a protected, secure REST API for consumption by Employees-R-US web hooks.
The initial REST API will consist of 3 endpoints, listed in the following section. If for any reason the implementation 
of an endpoint is problematic, the team lead will accept **pseudo-code** and a pertinent description (e.g. java-doc) of intent.

Good luck!

## Endpoints to implement (API module)

_See `com.challenge.api.controller.EmployeeController` for details._

getAllEmployees()

    output - list of employees
    description - this should return all employees, unfiltered

getEmployeeByUuid(...)

    path variable - employee UUID
    output - employee
    description - this should return a single employee based on the provided employee UUID

createEmployee(...)

    request body - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

## Code Formatting

This project utilizes Gradle plugin [Diffplug Spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle) to enforce format
and style guidelines with every build.

To format code according to style guidelines, you can run **spotlessApply** task.
`./gradlew spotlessApply`

The spotless plugin will also execute check-and-validation tasks as part of the gradle **build** task.
`./gradlew build`

# API Testing Guide
 
This section explains exactly how to test the employee REST API locally using Postman or curl.
 
## 1. Start the application
 
From the project root, run:
 
```bash
gradlew.bat bootRun
```
 
If you are on Linux or macOS, use:
 
```bash
./gradlew bootRun
```
 
The application should start on:
 
```text
http://localhost:8080
```
 
---
 
## 2. Authentication
 
The API is protected with Basic Authentication.
 
### Credentials
- Username: admin
- Password: admin123
These values are configured in `application.yml`.
 
### In Postman
1. Open the request.
2. Go to the Authorization tab.
3. Select Basic Auth.
4. Enter:
   - Username: admin
   - Password: admin123
### In curl
Use:
 
```bash
curl -u admin:admin123 http://localhost:8080/api/v1/employee
```
 
---
 
## 3. Base URL
 
Use this base URL for all requests:
 
```text
http://localhost:8080
```
 
---
 
## 4. Endpoints
 
### 4.1 Get all employees
 
Method: GET
 
URL:
 
```text
http://localhost:8080/api/v1/employee
```
 
#### Postman
- Method: GET
- URL: http://localhost:8080/api/v1/employee
- Authorization: Basic Auth with admin/admin123
#### curl
```bash
curl -u admin:admin123 http://localhost:8080/api/v1/employee
```
 
Expected result:
- Status code: 200 OK
- Response: JSON array of employee objects
---
 
### 4.2 Get one employee by UUID
 
Method: GET
 
URL:
 
```text
http://localhost:8080/api/v1/employee/{uuid}
```
 
Replace `{uuid}` with an actual employee UUID from the GET all employees response.
 
#### Postman
- Method: GET
- URL: http://localhost:8080/api/v1/employee/123e4567-e89b-12d3-a456-426614174000
- Authorization: Basic Auth
#### curl
```bash
curl -u admin:admin123 http://localhost:8080/api/v1/employee/123e4567-e89b-12d3-a456-426614174000
```
 
Expected result:
- Status code: 200 OK
- Response: one employee object
---
 
### 4.3 Create a new employee
 
Method: POST
 
URL:
 
```text
http://localhost:8080/api/v1/employee
```
 
#### Important
This request requires a JSON body in the request body.
Do not put the data in query parameters.
 
#### Where to add the JSON in Postman
1. Open the request.
2. Go to the Body tab.
3. Select raw.
4. Choose JSON from the dropdown.
5. Paste the JSON object into the body.
#### Where to add the JSON in curl
Use the `-d` option with a JSON payload.
 
---
 
## 5. Employee create request body
 
The request body must be a JSON object with these fields:
 
```json
{
  "firstName": "Srushti",
  "lastName": "Bhuyar",
  "fullName": "Srushti Bhuyar",
  "salary": 120000,
  "age": 24,
  "jobTitle": "Software Engineer",
  "email": "srushti.bhuyar@example.com",
  "contractHireDate": "2024-01-15T10:00:00Z",
  "contractTerminationDate": "2025-01-15T10:00:00Z"
}
```
 
### Field rules
- firstName: required, letters only
- lastName: required, letters only
- fullName: required, letters and spaces only
- salary: required, must be greater than 0
- age: required, must be between 18 and 100
- jobTitle: required
- email: required, valid email format, lowercase only
- contractHireDate: optional
- contractTerminationDate: optional
### Example Postman request
- Method: POST
- URL: http://localhost:8080/api/v1/employee
- Authorization: Basic Auth
- Body:
  - raw
  - JSON
Paste this JSON:
 
```json
{
   "firstName": "Srushti",
  "lastName": "Bhuyar",
  "fullName": "Srushti Bhuyar",
  "salary": 120000,
  "age": 24,
  "jobTitle": "Software Engineer",
  "email": "srushti.bhuyar@example.com"
}
```
 
### Example curl request
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/v1/employee \
  -H "Content-Type: application/json" \
  -d '{
   "firstName": "Srushti",
  "lastName": "Bhuyar",
  "fullName": "Srushti Bhuyar",
  "salary": 120000,
  "age": 24,
  "jobTitle": "Software Engineer",
  "email": "srushti.bhuyar@example.com"
  }'
```
 
Expected result:
- Status code: 201 Created
- Response: created employee object
---
 
## 6. Example success response
 
A successful create response will look like this:
 
```json
{
  "uuid": "some-generated-uuid",
   "firstName": "Srushti",
  "lastName": "Bhuyar",
  "fullName": "Srushti Bhuyar",
  "salary": 120000,
  "age": 24,
  "jobTitle": "Software Engineer",
  "email": "srushti.bhuyar@example.com",
  "contractHireDate": null,
  "contractTerminationDate": null
}
```
 
---
 
## 7. Example invalid request
 
If you send invalid data, the API returns 400 Bad Request.
 
Example invalid payload:
 
```json
{
  "firstName": "Srushti2",
  "lastName": "Bhuyar!",
  "fullName": "Srushti 123",
  "salary": 0,
  "age": 17,
  "jobTitle": "Software Engineer",
  "email": "srushti@Example.com"
}
```
 
This fails because:
- firstName contains a number
- lastName contains a special character
- fullName contains a number
- salary is zero
- age is below 18
- email is not lowercase
---
 
## 8. Common status codes
 
- 200 OK: successful GET
- 201 Created: successful employee creation
- 400 Bad Request: invalid request data
- 401 Unauthorized: missing or incorrect authentication
- 409 Conflict: duplicate email
---
 
## 9. Quick checklist for testing
 
- Start app
- Open Postman or terminal
- Add Basic Auth with admin/admin123
- For GET requests: no JSON body needed
- For POST requests: add JSON in the request body
- Use lowercase email
- Use letters only for names
- Use salary greater than 0
- Use age between 18 and 100
