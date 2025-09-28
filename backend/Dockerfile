# Stage 1: Build the Maven project
FROM public.ecr.aws/jht/maven:3.8.3-openjdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Create the final image with Amazon Corretto
FROM public.ecr.aws/amazoncorretto/amazoncorretto:8
WORKDIR /app
COPY --from=build /app/target/pasteau-sahel.jar .
COPY --from=build /app/target/lib ./lib

# Expose the HTTP port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "pasteau-sahel.jar"]