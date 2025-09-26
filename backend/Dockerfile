# Stage 1: Build the Maven project
FROM maven:3.8-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Create the final image with WildFly
FROM jboss/wildfly
# The .war file is copied to the deployments directory, where WildFly will automatically deploy it.
COPY --from=build /app/target/pasteau-sahel.war /opt/jboss/wildfly/standalone/deployments/

# Expose the HTTP port
EXPOSE 8080

# Start WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]