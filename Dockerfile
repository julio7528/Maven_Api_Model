# Estágio 1: Build da aplicação com Maven
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml para baixar as dependências primeiro (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn package -DskipTests

# Estágio 2: Criação da imagem final com JRE
FROM openjdk:17-jre-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR da aplicação do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação usa (conforme application.properties)
EXPOSE 8087

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]