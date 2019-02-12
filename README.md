# Bill Generator
This is stand alone Java application which generates a bill with sub total amount, discount amount and total due after discount. 
Prices of items and their discounts whether they are flat discounts or multi buy 
discounts is mentioned in YAML file.

 - Prices and discounts are stored in a YAML fil 
 - All tests are integration tests, so some tests are redundant.

## Technical Debt
- Prices and discounts are to be read from a persistent data source
- Add Sonar for checking code quality
- Add logging framework like log4j or similar ones
- Add more robust logging and commenting on code
- If required, unit tests can be implemented using Mockito and argument matchers etc.

## How to run

Clone the code from github with command 

https://github.com/akaladhar/bill-generator.git

Import project into IDE of your choice as a gradle project and run

`gradle clean build`

Above command will build the artifact.

To run the application, set your classpath appropriately and execute java command with PriceBasket with appropriate items to buy. Sample command is given below.

`java PriceBasket Apple Bread`

