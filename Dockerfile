FROM openjdk:21

WORKDIR /app

COPY . /app

COPY wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

RUN ./mvnw -B clean package -DskipTests

EXPOSE 8082

CMD ["./wait-for-it.sh", "projeto-usuariosapi-db", "5432", "--", "java", "-jar", "projetoUsuariosApi-0.0.1-SNAPSHOT.jar"]