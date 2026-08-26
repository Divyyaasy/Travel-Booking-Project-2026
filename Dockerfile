FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/travel-booking-app-1.0.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]
