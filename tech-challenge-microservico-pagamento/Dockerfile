FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src
RUN mvn clean install -DskipTests


FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8082
COPY --from=build /app/target/techchallenge-microservico-pagamento-1.0.0.jar techchallenge-microservico-pagamento.jar

ARG DB_ENDPOINT
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_REGION

ENV DB_ENDPOINT=$DB_ENDPOINT
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
ENV AWS_REGION=$AWS_REGION
ENV JAVA_APP_ARGS="--spring.config.location=/src/main/resources/application.properties"

ENTRYPOINT ["java","-jar","techchallenge-microservico-pagamento.jar", "$JAVA_APP_ARGS"]
