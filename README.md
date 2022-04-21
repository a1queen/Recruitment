# Recruitment
Project in Java with Spring Boot written as a recruitment project. </br>
To save data from Flight Entity use POST and send generated JSON in @RequestBody as plain text</br>
To add data from Cargo Entity use PATCH and send generated JSON in @RequestBody as plain text</br>
To get data from task 1 use GET request sent to "flights/specific" and send JSON with specified Flight Number and date e.g. </br>
{ </br>
    "flightNumber": 5978,</br>
    "departureDate": "2016-12-26T01:20:47-01:00"</br>
  }</br>
 To get data from task 2 use GET request sent to "flights/IATA" and send JSON with specified IATA Airport Code and date e.g. </br>
{ </br>
    "AirportIATACode" : "MIT", </br>
    "departureDate": "2016-12-26T01:20:47-01:00" </br>
} </br>
