FROM openjdk:8-jdk-alpine

RUN addgroup -S coupon && adduser -S coupon -G coupon
USER coupon:coupon

COPY target/rest-service-1.0.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
