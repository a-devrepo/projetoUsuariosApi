FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y netcat-openbsd postgresql-client

COPY wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

RUN ./mvnw -B clean package -DskipTests

EXPOSE 8082

CMD ["./wait-for-it.sh", "projeto-usuariosapi-db", "5432", "--", "java", "-jar", "/app/target/projetoUsuariosApi-0.0.1-SNAPSHOT.jar"]