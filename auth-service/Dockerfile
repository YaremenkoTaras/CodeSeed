FROM amazoncorretto:21-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 4001
ENTRYPOINT ["java","-jar","/app.jar"]
