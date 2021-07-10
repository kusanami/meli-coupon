FROM openjdk:8-jdk-alpine

RUN addgroup -S coupon && adduser -S coupon -G coupon
USER coupon:coupon

ARG JAR_FILE=application/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
