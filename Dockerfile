FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S appuser && adduser -S appuser -G appuser
USER appuser:appuser
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]