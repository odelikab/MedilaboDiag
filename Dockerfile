FROM openjdk:17-alpine
WORKDIR /opt
ENV PORT 8084
EXPOSE 8084
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar