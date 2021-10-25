FROM openjdk:17
EXPOSE 8090
ADD /target/satisfaction-0.0.1-SNAPSHOT.jar docker-client.jar
ENTRYPOINT ["java","-jar","docker-client.jar"]
