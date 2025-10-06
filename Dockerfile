FROM openjdk:21-jdk-slim

ENV WORK_DIR=/app \
    MAVEN_HOME=/usr/share/maven \
    MAVEN_VERSION=3.9.6 \
    PATH=$MAVEN_HOME/bin:$PATH

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        wget \
        unzip \
        git \
        ca-certificates && \
    update-ca-certificates && \
    wget -4 https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip -O /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /usr/share && \
    mv /usr/share/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    rm /tmp/maven.zip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR ${WORK_DIR}
COPY . .
ENTRYPOINT ["mvn", "clean", "test"]
