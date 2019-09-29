FROM anapsix/alpine-java:latest

VOLUME /tmp

ADD build/libs/kakaoapi-0.0.1-SNAPSHOT.jar notifier.jar

ENTRYPOINT ["java", "-jar", "notifier.jar"]

