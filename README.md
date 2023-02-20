# momiji-treehouse

Momiji Crawler System is a microservice-based system which primary purpose is to obtain data from online marketplaces.
Momiji Treehouse is a part of microservices that makes Momiji Crawler System. This service is mainly responsible for finding lists of products in ecommerce pages, which will then be passed to another service in Momiji Crawler System. To decouple these services, our crawler system is using RabbitMQ to pass messages between services.


## Using technologies:
- Java 19
- Spring Boot
- Spring AMQP
- RabbitMQ

## Install Requirements:
- Java SDK 19
- Project Lombok
- RabbitMQ Server
