FROM adoptopenjdk/openjdk11:alpine-jre

ADD build/libs/socshared-adapter-fb-1.0.0-SNAPSHOT.jar /app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar