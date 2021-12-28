FROM openjdk:8-jdk-alpine AS build
WORKDIR /workspace/app

COPY . /workspace/app
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY serviceAccount.json serviceAccount.json
COPY build/libs/customer-0.0.1-SNAPSHOT.jar customer-vaidh.jar
ENTRYPOINT ["java","-jar","/customer-vaidh.jar"]