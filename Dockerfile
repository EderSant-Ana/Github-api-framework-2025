# 1. IMAGEM BASE: Usa uma imagem do Alpine Linux com Java 21 instalada (leve e segura)
FROM openjdk:21-jdk-slim

# 2. DEFINIR VARIÁVEIS DE AMBIENTE
# Define o diretório de trabalho padrão dentro do container
ENV WORK_DIR=/app
# Define as variáveis de ambiente do Maven (Melhora a performance)
ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_VERSION=3.9.6
ENV PATH=$MAVEN_HOME/bin:$PATH

# 3. INSTALAR DEPENDÊNCIAS (Maven)
# Instala utilitários necessários e baixa o Maven
RUN apt-get update && \
    apt-get install -y wget unzip build-essential git && \
    wget https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.zip -O /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /usr/share && \
    mv /usr/share/apache-maven-$MAVEN_VERSION $MAVEN_HOME && \
    rm /tmp/maven.zip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 4. PREPARAR O PROJETO
# Cria o diretório de trabalho
RUN mkdir $WORK_DIR
WORKDIR $WORK_DIR

# 5. COPIAR O CÓDIGO FONTE
# Copia o código para dentro do container
# O .dockerignore deve listar 'target/' para evitar copiar lixo
COPY . $WORK_DIR

# 6. EXECUTAR OS TESTES
# Define o comando de entrada. Ele executará os testes
# O 'mvn clean test' gera os resultados na pasta target/surefire-reports/
ENTRYPOINT ["mvn", "clean", "test"]

# Para o ReportNG, o relatório HTML final estará em:
# /app/target/surefire-reports/html/index.html