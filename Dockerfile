FROM openjdk:8-jdk as builder

WORKDIR /root

COPY ${JAR_FILE} trip-1.8.0.jar

CMD java -jar -Dspring.profiles.active=${active} trip-1.8.0.jar