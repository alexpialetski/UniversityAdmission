FROM java:8
FROM maven:alpine

# image layer
WORKDIR /app
# ADD pom.xml /app
ADD . /app
RUN mvn verify clean

# Image layer: with the application
RUN mvn -v
RUN mvn install -DskipTests
EXPOSE 8080

ENTRYPOINT ["mvn","tomcat7:run"]
