# Imagem base oficial com Maven 3.9.6 + JDK 21 (Eclipse Temurin)
FROM maven:3.9.6-eclipse-temurin-21

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo de configuração primeiro (para aproveitar cache)
COPY pom.xml .

# Baixa dependências Maven antes de copiar o restante (cache mais eficiente)
RUN mvn -B dependency:go-offline

# Copia o restante do código-fonte do projeto
COPY src ./src

# Executa os testes automatizados
ENTRYPOINT ["mvn", "clean", "test"]
