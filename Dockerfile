FROM openjdk:8-jdk-alpine
RUN echo "Asia/Seoul" > /etc/timezone
VOLUME /tmp
ARG JAR_FILE
COPY ./target/infraBackend-1.0.0.jar  app.jar
COPY ./scouter/scouter.agent.jar  scouter.agent.jar
COPY ./scouter/scouter.conf scouter.conf

ENTRYPOINT ["java","-javaagent:/scouter.agent.jar","-Dscouter.config=/scouter.conf","-Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
