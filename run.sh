#!/bin/bash
# Script pour compiler et exécuter le projet Spring Boot sur Linux

# Vérifier Maven
if ! command -v mvn &> /dev/null
then
    echo "Maven n'est pas installé. Installez-le avec : sudo apt install maven"
    exit
fi

# Compiler tous les fichiers Java
echo "Compilation des fichiers Java..."
mvn clean compile -DskipTests
if [ $? -ne 0 ]; then
    echo "Erreur de compilation"
    exit 1
fi

# Créer le package JAR
echo "Création du JAR..."
mvn package -DskipTests
if [ $? -ne 0 ]; then
    echo "Erreur lors de la création du JAR"
    exit 1
fi

# Vérifier si le JAR existe
JAR_FILE=$(ls target/*.jar | grep -v 'original' | head -n 1)
if [ ! -f "$JAR_FILE" ]; then
    echo "JAR non trouvé dans target/"
    exit 1
fi

# Exécuter le JAR
echo "Lancement du projet..."
java -jar "$JAR_FILE"
