
FROM openjdk:17
RUN mkdir /app
COPY target/petclinic-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
