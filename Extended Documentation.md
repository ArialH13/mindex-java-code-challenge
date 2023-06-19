### Task 1 Response:
### How to Use
The following endpoint is now available for getting the new type, ReportingStructure:
```
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/reporting-structure/{employeeId}
    * RESPONSE: ReportingStructure
```

ReportingStructure has a JSON schema of:
```json
{
  "type":"ReportingStructure",
  "properties": {
    "employee": {
      "type" : "Employee"
      },
    "numberOfReports": {
      "type": "int"
    }
}
```


### Task 2 Response:
The following endpoints are now available for the new type, Compensation:

```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/compensation/{employeeId}
    * RESPONSE: Compensation
```

Compensation has a JSON schema of:
```json
{
  "type":"Compensation",
  "properties": {
    "employee": {
      "type" : "Employee"
    },
    "salary": {
      "type": "int"
    },
    "effectiveDate": {
      "type": "string"
    }
}
```

Additional Notes about usage:

-A compensation can only be created with a valid employeeId of an Employee that currently exists in the database.

-When creating a compensation, the only required field of employee is the employeeId. All other employee fields will be ignored.

-For both create and read, the Compensation is returned with a fully populated employee.


### Example - Using CREATE:
URL: localhost:8080/compensation

HTTP Method: POST

Body:
```json
{
    "employee": {
      "employeeId" : "b7839309-3348-463b-a7e3-5de1c168beb3"
    },
    "salary": 1000000,
    "effectiveDate" : "06/22/2018"
}
```

Response:
```json
{
    "employee": {
        "employeeId": "b7839309-3348-463b-a7e3-5de1c168beb3",
        "firstName": "Paul",
        "lastName": "McCartney",
        "position": "Developer I",
        "department": "Engineering",
        "directReports": null
    },
    "salary": 1000000,
    "effectiveDate": "06/22/2018"
}
```



