@echo off
REM Script pour compiler et exécuter le projet Spring Boot sur Windows

REM Vérifier si Maven est installé
where mvn >nul 2>nul
IF %ERRORLEVEL% NEQ 0 (
    echo Maven n'est pas installé. Installez-le et ajoutez-le au PATH.
    exit /b 1
)

REM Compiler tous les fichiers Java
echo Compilation des fichiers Java...
mvn clean compile -DskipTests
IF %ERRORLEVEL% NEQ 0 (
    echo Erreur de compilation
    exit /b 1
)

REM Créer le package JAR
echo Creation du JAR...
mvn package -DskipTests
IF %ERRORLEVEL% NEQ 0 (
    echo Erreur lors de la creation du JAR
    exit /b 1
)

REM Vérifier si le JAR existe
for %%f in (target\*.jar) do (
    set "JAR_FILE=%%f"
    REM Ignorer le fichier original généré par Spring Boot
    echo %JAR_FILE% | findstr /i "original" >nul
    IF %ERRORLEVEL% NEQ 0 (
        goto runJar
    )
)
echo JAR non trouve dans target\
exit /b 1

:runJar
echo Lancement du projet...
java -jar "%JAR_FILE%"
pause
