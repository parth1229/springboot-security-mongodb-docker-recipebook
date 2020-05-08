FROM openjdk:8-jdk-alpine
ADD build/libs/recipe-book-service.jar recipe-book-service.jar
EXPOSE 8081:8081
ENTRYPOINT ["java","-jar","recipe-book-service.jar"]