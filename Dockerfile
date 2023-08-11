FROM maven:3.8.4-openjdk-17 AS build
RUN mkdir -p workspace
WORKDIR workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#COPY frontend /workspace/frontend
#COPY dummy_data.csv /workspace
RUN mvn -f pom.xml clean install -DskipTests=true
##RUN ./mvnw clean package -DskipTests
#
##FROM adoptopenjdk/openjdk11:alpine-jre
FROM openjdk:16-ea-29-oraclelinux8
COPY --from=build /workspace/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]


#FROM adoptopenjdk/openjdk17:alpine-jre

#COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]