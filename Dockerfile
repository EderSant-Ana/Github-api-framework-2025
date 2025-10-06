FROM openjdk:21-jdk-slim

ENV WORK_DIR=/app \
    MAVEN_HOME=/usr/share/maven \
    MAVEN_VERSION=3.9.6 \
    PATH=$MAVEN_HOME/bin:$PATH

SHELL ["/bin/bash", "-xe", "-o", "pipefail", "-c"]

RUN apt-get update || (rm -rf /etc/apt/sources.list.d/* && apt-get update) && \
    apt-get install -y --no-install-recommends \
        apt-transport-https \
        ca-certificates \
        wget \
        curl \
        unzip \
        build-essential \
        git && \
    update-ca-certificates && \
    echo "Baixando Maven..." && \
    wget -q "https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip" -O /tmp/maven.zip || \
    curl -fsSL "https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip" -o /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /usr/share && \
    mv /usr/share/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    rm /tmp/maven.zip && \
    apt-get purge -y --auto-remove curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR ${WORK_DIR}

COPY . .

ENTRYPOINT ["mvn", "clean", "test"]
