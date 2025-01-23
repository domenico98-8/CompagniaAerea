# Usa un'immagine di base con Java 21 (OpenJDK)
FROM openjdk:21-jdk-slim AS build

# Installa Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*
	
# Imposta la directory di lavoro
WORKDIR /Progetto_Cybersecurity

# Copia il pom.xml e le dipendenze di Maven
COPY pom.xml /Progetto_Cybersecurity/

# Installa le dipendenze
RUN mvn dependency:go-offline

# Copia il resto del progetto
COPY src /Progetto_Cybersecurity/src/

# Esegui la build del progetto
RUN mvn clean package -DskipTests

# Fase di esecuzione
FROM openjdk:21-jdk-slim

# Copia il JAR costruito nella fase di build
COPY --from=build /Progetto_Cybersecurity/target/Progetto_Cybersecurity-0.0.1-SNAPSHOT.jar /Progetto_Cybersecurity/Progetto_Cybersecurity-0.0.1-SNAPSHOT.jar


# Copia il file di configurazione
COPY src/main/resources/application.properties /Progetto_Cybersecurity/application.properties

# Comando per avviare l'applicazione Spring
ENTRYPOINT ["java", "-jar", "/Progetto_Cybersecurity/Progetto_Cybersecurity-0.0.1-SNAPSHOT.jar", "--spring.config.location=file:/Progetto_Cybersecurity/application.properties"]