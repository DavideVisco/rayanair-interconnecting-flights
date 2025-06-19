# Ryanair - Task 2 - Java/Spring - Interconnecting Flights

## Tech Stack

- Java 24
- Spring Boot 3.5.0
- Maven for dependency management
- Spring Cloud OpenFeign for requests to external APIs
- JUnit 5 and Mockito for testing
- 
---

## Running

```bash
mvn clean compile
mvn test
mvn clean package
- java -jar rayanair-interconnecting-flights-0.0.1-SNAPSHOT.jar
```


#### The application will start on http://localhost:8080

## API Endpoint

#### Get Interconnections
- GET /flight-interconnections/api/interconnections
- parameters ----> departure(not null) String IATA code   
                   arrival(not null) String IATA code  
                 departureDateTime(not null) LocalDateTime ISO format  
                 arrivalDateTime(not null) LocalDateTime ISO format  
#### Example Request
- GET http://localhost:8080/api/interconnections?departure=AAR&arrival=ZAD&departureDateTime=2025-06-01T07:00&arrivalDateTime=2025-06-21T21:00

#### Example Response
[
{
"stops":1,
"legs":[
{
"departureAirport":"AAR",
"arrivalAirport":"GDN",
"departureDateTime":"2025-06-19T14:50:00",
"arrivalDateTime":"2025-06-19T16:00:00"
},
{
"departureAirport":"GDN",
"arrivalAirport":"ZAD",
"departureDateTime":"2025-06-20T09:15:00",
"arrivalDateTime":"2025-06-20T11:20:00"
}
]
},
{
"stops":1,
"legs":[
{
"departureAirport":"AAR",
"arrivalAirport":"STN",
"departureDateTime":"2025-06-19T14:15:00",
"arrivalDateTime":"2025-06-19T15:00:00"
},
{
"departureAirport":"STN",
"arrivalAirport":"ZAD",
"departureDateTime":"2025-06-19T17:30:00",
"arrivalDateTime":"2025-06-19T20:50:00"
}
]
},
{
"stops":1,
"legs":[
{
"departureAirport":"AAR",
"arrivalAirport":"STN",
"departureDateTime":"2025-06-19T14:15:00",
"arrivalDateTime":"2025-06-19T15:00:00"
},
{
"departureAirport":"STN",
"arrivalAirport":"ZAD",
"departureDateTime":"2025-06-20T13:25:00",
"arrivalDateTime":"2025-06-20T16:45:00"
}
]
},
{
"stops":1,
"legs":[
{
"departureAirport":"AAR",
"arrivalAirport":"STN",
"departureDateTime":"2025-06-19T14:15:00",
"arrivalDateTime":"2025-06-19T15:00:00"
},
{
"departureAirport":"STN",
"arrivalAirport":"ZAD",
"departureDateTime":"2025-06-21T10:20:00",
"arrivalDateTime":"2025-06-21T13:40:00"
}
]
}
]