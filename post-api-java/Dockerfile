FROM gradle:4.8.1-jdk8 as build
LABEL maintainer="https://github.com/silencecorner"
USER root
WORKDIR /home/gradle/app
COPY protos /home/gradle/protos/
COPY post-api-java /home/gradle/app/
RUN gradle build -x test

FROM openjdk:8u171-jre-alpine3.8
LABEL maintainer="https://github.com/silencecorner"
VOLUME /tmp
COPY --from=build /home/gradle/app/build/libs/post-api-java.jar /app.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
EXPOSE 50051