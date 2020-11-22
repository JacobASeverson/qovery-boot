# build stage
FROM gradle:6.7.1-jdk11 as gradle-build

WORKDIR /usr/local/app

COPY . .

RUN gradle assemble --no-daemon
RUN ls -al

ARG JAR_FILE=build/libs/qovery-boot-*.jar

COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# final stage
FROM openjdk:11-jre-slim

RUN useradd --shell /bin/sh app

WORKDIR /var/app

COPY --from=gradle-build /usr/local/app/dependencies/ ./
COPY --from=gradle-build /usr/local/app/spring-boot-loader/ ./
COPY --from=gradle-build /usr/local/app/snapshot-dependencies/ ./
COPY --from=gradle-build /usr/local/app/application/ ./


RUN chown -R app:app .

USER app

EXPOSE 9090

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]