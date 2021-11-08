Front end:
1. angular js 1.8.2
2. angular-ui-router 
3. bootstrap 4.1.3

Back end:
1. Spring web
2. Spring Data JPA
3. DTO model mapper 

REST documentation:
1.  Swagger API  (open api)

RDBMS:
1. Docker
2. MySQL


### Java 8
Convert an Integer to localized month name: 
* https://stackoverflow.com/questions/1038570/how-can-i-convert-an-integer-to-localized-month-name-in-java

solution: 
* Month.of(1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.defaultLocale)

### TOMCAT
Quick reference: http://tomcat.apache.org/tomcat-8.5-doc/

Additional information:
* servlet-spec: https://javaee.github.io/servlet-spec/downloads/servlet-4.0/servlet-4_0_FINAL.pdf


### Spring MVC
Quick reference: https://spring.io/guides/gs/serving-web-content/

Additional information:

### Spring Mapper
Entity To DTO Conversion for a Spring REST API:
* https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application

### Swagger
Quick reference: https://www.baeldung.com/spring-rest-openapi-documentation

Additional information:
* https://habr.com/ru/post/541592/
* https://www.javainuse.com/spring/boot_swagger3

### Spring Data JPA
Quick reference: 
* https://docs.spring.io/spring-data/jpa/docs/1.7.0.DATAJPA-580-SNAPSHOT/reference/html/index.html
* https://spring.io/guides/gs/accessing-data-mysql/

Additional information:
* https://stackoverflow.com/questions/49954812/how-can-you-make-a-created-at-column-generate-the-creation-date-time-automatical (Auditing)
* https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
* https://www.youtube.com/watch?v=nyFLX3q3poY&t=798s

custom query:
* https://stackoverflow.com/questions/42966967/creating-a-custom-query-with-spring-data-jpa

### DOCKER
Quick reference: https://hub.docker.com/_/mysql

setup DB:
* user: root
* pwd: 12345
* tag: mysql:latest
* port: -p 3306:3306

command:
1. docker pull mysql
2. docker run -d -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=12345 -d mysql:latest

### MSQL

CREATE DATABASE Statement: https://dev.mysql.com/doc/refman/8.0/en/create-database.html

example: 
* CREATE {DATABASE | SCHEMA} [IF NOT EXISTS] db_name
* CREATE SCHEMA db_test_data;

GROUP by:

EXTRACT(unit FROM date)
* https://stackoverflow.com/questions/508791/mysql-query-group-by-day-month-year
* https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_extract

Date and Time Functions:
* MONTHNAME: https://dev.mysql.com/doc/refman/8.0/en/date-and-time-functions.html#function_month

### FLYWAY
Quick reference: https://flywaydb.org/documentation/
