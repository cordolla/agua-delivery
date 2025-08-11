FROM maven:3.9-eclipse-temurin-17-focal AS build
WORKDIR /app

# Copia todos os POMs para o Maven entender a estrutura do projeto
COPY pom.xml .
COPY discovery-service/pom.xml ./discovery-service/
COPY product-service/pom.xml ./product-service/
COPY order-service/pom.xml ./order-service/
COPY gateway-service/pom.xml ./gateway-service/
COPY payment-service/pom.xml ./payment-service/

# Copia o código-fonte APENAS do módulo atual
COPY discovery-service/src ./discovery-service/src

# Constrói apenas o módulo atual
RUN mvn -B -f pom.xml clean package -DskipTests -pl discovery-service -am

# Estágio 2: Imagem Final de Execução
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia o .jar gerado do módulo atual
COPY --from=build /app/discovery-service/target/*.jar app.jar

# A porta padrão do Eureka Server é 8761
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]