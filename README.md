# Bookie Application

Bookie is a Java application built with Spring Boot and Maven. It is a library management system that allows patrons to borrow and return books.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development.

### Prerequisites

- Java 21
- Maven

### Installing

Clone the repository:

```bash
git clone https://github.com/timibacco/Bookie.git

```



```bash
cd Bookie
```
```bash
mvn clean install
```

### Running the Application

#### Run the application with the following command:

```bash
mvn spring-boot:run
```

#### To create an admin user, run the application with the --create-admin argument:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--create-admin"
```




