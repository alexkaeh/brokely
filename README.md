# broke.ly
__Text-based money transfer application in Jave, using Spring JPA and a RESTful API to allow access to persistent data.__
*Created by Aaron Hinjosa, Alec Hoskins, and Alex Kaehler*

The msot important new concept in this application is the use of a relational database. The Spring framework allowed us to creat3e a persistent webserver that could manage user authentication and perform CRUD operations on our data. We created appropriate entities mapped to the tables in the database, a layer of business logic to process and validate our data, and authenticated API endpoints available to our front end.

The front end is text-based and also written in Java, using RestTemplate to generate requests to the server.

A user can register and log in, where they can access their dashboard. From there they can send and request money, as well as approve or reject inbound requests.
