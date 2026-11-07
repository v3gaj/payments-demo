FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre
COPY --from=build /app/target/payments-demo-1.0.0.jar /app.jar
ENV JAVA_OPTS="-Xmx300m -Xss256k -XX:+UseContainerSupport"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app.jar"]
