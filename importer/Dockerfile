FROM gradle:8.0-jdk17 AS BUILD
WORKDIR /build
COPY . .
RUN gradle bootJar
RUN cp build/libs/importer-0.0.1-SNAPSHOT.jar app.jar

FROM openjdk:17
WORKDIR /app
COPY --from=BUILD /build/app.jar app.jar
ENV TZ Europe/Berlin
CMD java -jar app.jar