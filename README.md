## Technologies:

- Java 21
- Spring Boot 3
- Gradle
- H2 in-memory database

## Run the application:

`./gradlew bootRun` in the terminal

## Database access:

- H2 Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: admin

## Endpoints:

### Collection Boxes:

- GET /v1/collectionBoxes - list all boxes
- POST /v1/collectionBoxes - create new box
- POST /v1/collectionBoxes/{id}/addMoney - add money
- POST /v1/collectionBoxes/{boxId}/assign/{eventId} - assign to event
- POST /v1/collectionBoxes/{boxId}/empty - empty box (transfer money)

### Events:

- GET /v1/fundraisingEvents - list all events
- POST /v1/fundraisingEvents - create new event
- GET /v1/fundraisingEvents/report - financial report

## Sample queries:

- Create fundraising event:

`
curl -H "Content-Type: application/json" -X POST \
-d '{
"name": "Pet Fund",
"description": "For pets",
"currency": "EUR"
}' \
http://localhost:8080/v1/fundraisingEvents
`

- Create collection box:

`
curl -H "Content-Type: application/json" -X POST \
-d '{
"name": "Pomoc dla schroniska",
"description": "Zbieramy na karmę dla psów",
"targetAmount": 1000.00,
"currency": "PLN"
}' \
http://localhost:8080/v1/collectionBoxes
`

- Assign box to event:

`
curl -X POST http://localhost:8080/v1/collectionBoxes/1/assign/1
`

- Add money to collection box:

`
curl -H "Content-Type: application/json" -X POST \
-d '{
"amount": 155,
"currency": "PLN"
}' \
http://localhost:8080/v1/collectionBoxes/1/addMoney
`

- Empty box (transfer money with currency conversion):

`
curl -X POST http://localhost:8080/v1/collectionBoxes/1/empty
`

- Check event balance after transfer:

`
curl -X GET http://localhost:8080/v1/fundraisingEvents/1
`

- View financial report:

`
curl -X GET http://localhost:8080/v1/fundraisingEvents/report
`

- Get collection boxes status (without amounts):

`
curl -X GET http://localhost:8080/v1/collectionBoxes/status
`

## Postman testing:

You can test endpoints with sample queries in Postman by importing the collection file from the [postman](postman)
folder - simply drag and drop the JSON file into Postman or use the Import button.

