FROM maven:3.6.3-jdk-11
WORKDIR /home/tmp

ADD . .

RUN mvn package -DskipTests

WORKDIR /home/app
RUN mv /home/tmp/target/* /home/app; \
    rm -rf /home/tmp

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "tamada-0.0.1-SNAPSHOT.jar"]