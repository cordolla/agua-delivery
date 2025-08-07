# Estágio 1: Build com Maven
FROM maven:3.9-eclipse-temurin-17-focal AS build

WORKDIR /app

# COPIA OS POMs DE TODOS OS MÓDULOS para que o Maven entenda a estrutura do projeto
COPY pom.xml .
COPY discovery-service/pom.xml ./discovery-service/
COPY product-service/pom.xml ./product-service/
COPY order-service/pom.xml ./order-service/

# COPIA O CÓDIGO-FONTE APENAS DO MÓDULO ATUAL
COPY discovery-service/src ./discovery-service/src

# Constrói apenas o módulo 'discovery-service'. Agora o Maven encontrará as pastas
# dos outros módulos (mesmo que contenham apenas o pom.xml) e não vai dar erro.
RUN mvn -B -f pom.xml clean package -DskipTests -pl discovery-service -am

# Estágio 2: Imagem final de execução
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copia o .jar gerado para a imagem final
COPY --from=build /app/discovery-service/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]