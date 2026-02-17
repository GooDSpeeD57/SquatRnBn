FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="stagiaire@devops-afpa.fr"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]