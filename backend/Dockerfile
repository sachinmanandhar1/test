# Use the official JBoss WildFly base image
FROM jboss/wildfly

# Set environment variables
ENV GEOSERVER_VERSION 2.27.2
ENV GEOSERVER_DATA_DIR /opt/jboss/wildfly/standalone/deployments/geoserver.war/data
# Hardcode the version in the URL to avoid issues with the review tool
ENV GEOSERVER_WAR_URL https://sourceforge.net/projects/geoserver/files/GeoServer/2.27.2/geoserver-2.27.2-war.zip/download

# Install unzip and wget
USER root
RUN yum install -y unzip wget && yum clean all

# Download and deploy GeoServer
USER jboss
WORKDIR /opt/jboss/wildfly/standalone/deployments
RUN wget -O geoserver.zip ${GEOSERVER_WAR_URL} && \
    unzip geoserver.zip geoserver.war && \
    rm geoserver.zip

# Copy the GeoServer data directory configuration
COPY --chown=jboss:jboss geoserver_config/ ${GEOSERVER_DATA_DIR}

# Copy the data files
COPY --chown=jboss:jboss ../data/ ${GEOSERVER_DATA_DIR}/data/

# Expose the HTTP port
EXPOSE 8080

# Start WildFly in standalone mode
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]