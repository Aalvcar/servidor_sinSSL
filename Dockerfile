FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el proyecto completo
COPY . .

# Instalar ant para compilar
RUN apt-get update && apt-get install -y ant

# Compilar el proyecto
RUN ant jar

# Ejecutar especificando el classpath correctamente
CMD ["java", "-cp", "dist/Alvarez_Cardenas_Antonio_Tarea4PSP.jar:lib/*", "servidor_main.Servidor"]
