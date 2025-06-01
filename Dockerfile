# Etapa 1: build do projeto
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final com Java 21 e dockerize
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instala curl e baixa o dockerize
ENV DOCKERIZE_VERSION v0.9.3

RUN apt-get update \
    && apt-get install -y wget \
    && wget -O - https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz | tar xzf - -C /usr/local/bin \
    && apt-get autoremove -yqq --purge wget && rm -rf /var/lib/apt/lists/*

# Copia o JAR gerado na etapa 1
COPY --from=builder /app/target/*.jar app.jar

# Espera o MySQL e inicia a aplicação
ENTRYPOINT ["dockerize", "-wait", "tcp://mysql:3306", "-timeout", "30s", "java", "-jar", "app.jar"]
