FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=production"]
