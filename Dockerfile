# Imagem base com JDK 21
FROM openjdk:21-jdk-slim

# Variáveis de ambiente
ENV WORK_DIR=/app \
    MAVEN_HOME=/usr/share/maven \
    MAVEN_VERSION=3.9.6 \
    PATH=$MAVEN_HOME/bin:$PATH

# Instala dependências, Maven e limpa cache
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget unzip build-essential git && \
    wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip -O /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /usr/share && \
    mv /usr/share/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    rm /tmp/maven.zip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Define o diretório de trabalho
WORKDIR ${WORK_DIR}

# Copia o código fonte para dentro do container
COPY . .

# Define o comando de entrada
ENTRYPOINT ["mvn", "clean", "test"]
