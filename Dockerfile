FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y ant
RUN ant jar
CMD ["java", "-jar", "dist/Alvarez_Cardenas_Antonio_Tarea4PSP.jar"]
