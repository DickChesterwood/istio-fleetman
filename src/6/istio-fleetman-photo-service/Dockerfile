FROM arm64v8/amazoncorretto:17.0.5-al2022-RC-headless

MAINTAINER Richard Chesterwood "contact@virtualpairprogrammers.com"

ADD target/photoservice-0.0.1-SNAPSHOT.jar webapp.jar

EXPOSE 8080

CMD ["java", "-jar","webapp.jar"]
