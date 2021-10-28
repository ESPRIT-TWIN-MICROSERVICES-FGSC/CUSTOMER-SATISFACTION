FROM openjdk:17
VOLUME /tmp
EXPOSE 8090
ADD JAR_FILE=target/satisfaction-0.0.1-SNAPSHOT.jar docker-client.jar
# Add the application's jar to the container
ADD ${JAR_FILE} spring-boot-docker.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-client.jar"]
