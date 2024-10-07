# etiqaassessment

Java Technical Assessment for Etiqa

Customer and Product Management API

Table of Contents

1. Introduction
2. Technologies Used
3. Installation
4. Usage
5. API Endpoints
6. Testing
7. Logging
8. Error Handling
9. GitHub Actions



  1. Introduction
    This project is a Spring Boot application designed to manage Customers and Products through a RESTful API. It supports standard CRUD operations and provides API documentation via Swagger.
  
  2. Technologies Used
    Java 17
    Spring Boot 3.1.6
    Spring Data JPA
    H2 Database
    SpringDoc OpenAPI for API documentation
    Logback for logging
    JUnit 5 for unit testing
    Mockito for mocking dependencies
  
  3. Installation
    Clone the repository:
    git clone https://github.com/yourusername/your-repo.git
    cd your-repo
    
    Build the project:
    mvn clean install
    
    Run the application:
    mvn spring-boot:run
  
  4. Usage
    Once the application is running, you can access the API at http://localhost:8080/api
  
  5. API Endpoints
    Customer Endpoints
      GET	/customers	(Retrieve all customers)
      GET	/customers/{id}	(Retrieve a customer by ID)
      POST	/customers	(Create a new customer)
      PUT	/customers/{id}	(Update a customer by ID)
      PATCH	/customers/{id}	(Partially update a customer)
      DELETE	/customers/{id}	(Delete a customer by ID)
     
    Product Endpoints
      GET	/products	(Retrieve all products)
      GET	/products/{id}	(Retrieve a product by ID)
      POST	/products	(Create a new product)
      PUT	/products/{id}	(Update a product by ID)
      PATCH	/products/{id}	(Partially update a product)
      DELETE	/products/{id}	(Delete a product by ID)
  
  6. Testing
    To run the unit tests, execute the following command:
      mvn test
    All tests are located in the src/test/java/com/example/demo/service directory.
  
  7. Logging
    The application uses Logback for logging. Logs are recorded in the console and can be configured in src/main/resources/logback-spring.xml.
  
  8. Error Handling
    Custom exceptions are implemented for handling errors like CustomerNotFoundException and ProductNotFoundException. When an error occurs, a meaningful error message is returned in JSON format.
  
  9. GitHub Actions
    This repository includes GitHub Actions for continuous integration. The actions run tests on each push to the main branch.






