FROM openjdk:11
RUN mkdir -p /opt/app/
WORKDIR /opt/app/
COPY ./target/customer-service-1.0-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/customer-service-1.0-SNAPSHOT.jar"]