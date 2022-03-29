# Using multistage docker file: https://docs.docker.com/develop/develop-images/multistage-build/
FROM gradle:7.4.1 as builder

COPY build.gradle.kts .
COPY gradle.properties .
COPY settings.gradle.kts .
COPY detekt.yml .
COPY src ./src

# Build a release artifact.
RUN gradle shadowJar

FROM openjdk:17-jdk

EXPOSE 8080:8080

RUN mkdir /app

COPY --from=builder /home/gradle/build/libs/app.jar /app/

WORKDIR /app

CMD ["java", "-jar", "app.jar"]
