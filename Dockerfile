FROM openjdk:11-jre-slim
RUN echo "Asia/Seoul" > /etc/timezone
VOLUME /tmp
ARG JAR_FILE
COPY ./target/NewDealBackend-1.0.0.jar app.jar

ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]