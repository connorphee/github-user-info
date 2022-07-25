FROM amazoncorretto:17.0.4

COPY target/github-user-info-*.jar /github-user-info.jar

ENTRYPOINT ["java", "-jar", "github-user-info.jar"]
