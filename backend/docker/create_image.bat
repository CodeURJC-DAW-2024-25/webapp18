@echo off
REM Script para crear la imagen Docker de la aplicación

REM Nombre de la imagen y tag
SET IMAGE_NAME=dreamlife-webapp
SET TAG=latest

echo Construyendo la imagen Docker %IMAGE_NAME%:%TAG%...

REM Navegar al directorio raíz del proyecto (asumiendo que estamos en la carpeta docker)
cd ..

REM Construir la imagen utilizando el Dockerfile en el directorio docker
docker build -t %IMAGE_NAME%:%TAG% -f docker/Dockerfile .

echo Imagen construida correctamente: %IMAGE_NAME%:%TAG%