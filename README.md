# People Microservice

* Run 'mvn test' to see the test coverage
* Run 'mvn spring-boot:run' to see the application in action in your localhost:8888
* Append `-Dserver.port=<Port Number>` in case to run on a different port

###### In-Memory H2 DB admin console is accessible in below -
* http://localhost:8888/h2-console

Enter below settings for login -
* JDBC URL: `jdbc:h2:mem:testdb`
* Username: `sa`
* Password: `leave password field blank`