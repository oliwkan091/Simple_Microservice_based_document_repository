# Simple_Microservice_based_document_repository
Simple app I created to learn a bit about microservices. 

This is a simple file repository web service divided into 3 microservices and one client app. All services all connected via Eurek server. 
"Gateway" is responsible for maintaining connection and authorization of users, "Eureka_server" is used for connecting all services and "File_transfer" is responsible for maintenance of user files.
