# build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests


# run
FROM amazoncorretto:21.0.5
WORKDIR /appgit reset --soft HEAD~N

COPY --from=build ./build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_secret'

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='American/Sao_Paulo'

ENTRYPOINT java -jar libraryapi.jar