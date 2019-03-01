#giuseppe-adaldo-parental-control-service

This service is an API that shows the client if a user is allowed to watch a movie by providing:
user parental control level and movieId.

Allowed parental control levels are:

- __U__, universal;
- __PG__, Parental Guidance;
- __12__, not allowed for people younger than 12 years old;
- __15__, not allowed for people younger than 15 years old;
- __18__, not allowed for people younger than 18 years old;

Although the priority has not been specified, we can assume Universal is the lower allowance, then PG, 12, 15 and at 
the end 18.

Note
---
This is a simplified version of a real parental control service.

Movies database is for testing purpose only.

The MovieService has been mocked to be able to run and show off results but ideally this should be a microservice 
with a rest API, and MovieService is a Rest Interface to communicate through rest with another microservice.

Design
---
The key point here is to use an enumeration to handle the parental control levels. In this way, keeping the levels 
sorted by priority, we can simply compare the given level with the movie one taking advantage from the enumeration 
ordinal. 
To retrieve the correct Parental Control Level, we used a _getValueOf(String)_ helper method with __O(1)__ 
complexity. This is thanks to a Map, loaded statically.


Improvements
---
Use a Dependency injection framework to handle the dependencies and Spring boot to run as microservice.

Requirements
---

- java SDK 8 or later version;
- maven 3 or later;

Build
---

```bash
mvn clean install
```

Run
---
You can execute the service on existing movies by checking the catalogue:
```java
java -jar target/giuseppe-adaldo-parental-control-service.jar catalogue 
```

for help on running:
```java
java -jar target/giuseppe-adaldo-parental-control-service.jar help 
``` 

sample running for movie 'In the Fade':
```java
java -jar target/giuseppe-adaldo-parental-control-service.jar 18 "In the Fade" 
``` 
the above returns true

```java
java -jar target/giuseppe-adaldo-parental-control-service.jar U "In the Fade" 
``` 
the above returns false

```java
java -jar target/giuseppe-adaldo-parental-control-service.jar 18 "Blindspotting" 
``` 
the above returns true

```java
java -jar target/giuseppe-adaldo-parental-control-service.jar U "Blindspotting" 
``` 
the above returns true

```java
java -jar target/giuseppe-adaldo-parental-control-service.jar U "hello ketty" 
``` 
the above should return 'Movie not found with id=hello ketty'