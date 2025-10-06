FROM openjdk:21-jdk-slim

ENV WORK_DIR=/app
ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_VERSION=3.9.6
ENV PATH=$MAVEN_HOME/bin:$PATH

RUN apt-get update && 

apt-get install -y --no-install-recommends 

wget 

unzip 

build-essential 

git && 

wget https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.zip -O /tmp/maven.zip && 

unzip /tmp/maven.zip -d /usr/share && 

mv /usr/share/apache-maven-$MAVEN_VERSION $MAVEN_HOME && 

rm /tmp/maven.zip && 

apt-get clean && 

rm -rf /var/lib/apt/lists/*

RUN mkdir $WORK_DIR
WORKDIR $WORK_DIR

COPY . $WORK_DIR

ENTRYPOINT ["mvn", "clean", "test"]