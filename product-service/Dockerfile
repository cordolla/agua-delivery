# Estágio 1: Build com Maven
# Usamos uma imagem oficial do Maven com Java 17
FROM maven:3.9-eclipse-temurin-17-focal AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia todo o projeto (pom.xml e a pasta src)
COPY . .

# --- Início da sua nova estratégia de build ---

# 1. Usa o Maven da imagem para gerar os scripts do wrapper
RUN mvn -N wrapper:wrapper

# 2. Torna o script do wrapper executável
RUN chmod +x mvnw

# 3. Usa o wrapper para compilar o projeto e gerar o .jar
RUN ./mvnw clean package -DskipTests

# --- Fim da sua nova estratégia de build ---


# Estágio 2: Imagem final de execução
# Usamos uma imagem leve, apenas com o ambiente de execução Java 17
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Argumento para o nome do JAR, definido no docker-compose.yaml
ARG JAR_FILE=target/*.jar

# Copia o .jar gerado no estágio de build para a imagem final
COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]