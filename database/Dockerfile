FROM postgres:13

RUN apt-get update && \
    apt-get install -y --no-install-recommends postgresql-13-postgis-3 postgis && \
    rm -rf /var/lib/apt/lists/*