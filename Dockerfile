FROM openjdk:8-alpine

LABEL maintainer="Ian Mubangizi - io@ianmubngizi.com"
WORKDIR /app

COPY build/docker/libs libs/
COPY build/docker/resources resources/
COPY build/docker/classes classes/

ENV DEBUG=false \
    SCOPES=true \
    DDL_SETTING=create-drop \
    DATABASE_PORT=5432 \
    DATABASE=recommendo \
    DATABASE_HOST=localhost \
    DATABASE_PASSWORD=recommendo \
    DATABASE_USERNAME=recommendo \
    DATABASE_URL=jdbc:postgresql://$DATABASE_HOST:$DATABASE_PORT/$DATABASE \
    GOOGLE_CLIENTID=GOOGLECLIENTID \
    GOOGLE_CLIENTSECRET=GOOGLECLIENTSECRET \
    RESOURCE_ID=RESOURCEID \
    RESOURCE_SECRET=RESOURCESECRET

ENTRYPOINT ["java", "-cp", "/app/resources:/app/classes:/app/libs/*", "com.saivo.recommendo.ServerKt"]
EXPOSE 8080 8080
