# BRANCH COURSES REST API

## Overview

Branch Courses is a Spring Boot-based RESTful API that manages branch-wise courses available for every semester. The project utilizes various technologies such as Docker, MySQL, JPA, Hibernate, HAL Browser, Swagger OpenAPI, HATEOAS, Talend API, and more.

## Technologies Used

- **Spring Boot 3:** Framework for building Java-based enterprise applications.
- **Docker:** Containerization platform for packaging, distributing, and running applications.
- **MySQL:** Relational database management system.
- **JPA (Java Persistence API):** Java specification for managing relational data.
- **Hibernate:** Object-relational mapping framework.
- **HAL Browser:** Hypermedia API browser that simplifies REST API exploration.
- **Swagger OpenAPI:** API documentation tool for designing, building, and documenting APIs.
- **HATEOAS (Hypermedia as the Engine of Application State):** A constraint of the REST application architecture.
- **Talend API:** Integration tool for connecting, accessing, and managing APIs.

## Features Implemented

- **Dynamic Filtering:** Utilizes MappingJacksonValue for dynamic data filtering.
- **Versioning:** API versioning for backward compatibility.
- **Spring Security Authentication:** Ensures secure access to the API.
- **Database Connectivity:** Configured MySQL database for storing course information.
- **Content Negotiation:** Supports content negotiation between JSON and XML.
- **Internalization (i18N):** Internationalization for supporting multiple languages.
- **Exception Handling:** Proper handling of exceptions to provide meaningful error responses.
- **Validations:** Data validation to ensure the integrity of input data.
- **Documentation:** Comprehensive documentation using Swagger OpenAPI.

## Getting Started

### Prerequisites

- [Eclipse](https://www.eclipse.org/downloads/) - Integrated Development Environment.
- [Docker](https://www.docker.com/get-started) - Containerization platform.
- [MySQL](https://dev.mysql.com/downloads/) - Database management system.

### Installation

1. Import the project into Eclipse.
2. Install and run Docker.
3. Execute the Docker script provided in the `dockerScript.txt` file to set up MySQL connectivity.
  docker run --detach --env MYSQL_ROOT_PASSWORD=cseaiml0123 --env MYSQL_USER=cseaiml --env MYSQL_PASSWORD=cseaiml@123 --env MYSQL_DATABASE=department --name mysql_cse --publish 3309:3306 mysql:8-oracle
4. Find authentication credentials in the `application.properties` file.

## Usage

Explore the API using HAL Browser and Swagger OpenAPI documentation. Ensure proper authentication and use the provided features like dynamic filtering, versioning, and more.

Get The resources using the following URL:
/courses : gets you all the courses available
/courses/{courseId} : Gets you a specific course based upon the id.
/Courses/branch/{branch} : gets you all the courses of a branch.
/courses/semester/{semester} : gets you all the courses of a semester.
/about-us : gets you a description of the branch in many languages (currently only in french and english)

Post:
/courses: post a course in a proper format.

put:
/courses/{courseId} : Updates a specific course based upon the id.

Delete:
/courses/{courseId} : Deletes a specific course based upon the id.

## Contribution

Contributions are welcome! Feel free to submit issues, feature requests, or pull requests.
