# Imagem base oficial com Maven 3.9.6 + JDK 21 (Eclipse Temurin)
FROM maven:3.9.6-eclipse-temurin-21

# Define diretório de trabalho dentro do container
WORKDIR /app

# Copia o conteúdo do projeto para o container
COPY . .

# Usa o cache das dependências Maven para builds mais rápidos
# (essa linha baixa dependências antes de copiar todo o código)
RUN mvn -B dependency:go-offline

# Define o comando de entrada (executa os testes Maven original)
ENTRYPOINT ["mvn", "clean", "test"]
