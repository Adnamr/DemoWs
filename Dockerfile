FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demowslbvie.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","demowslbvie.jar"]
