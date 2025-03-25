@echo off
REM Script para publicar la imagen Docker en Docker Hub

REM Configuración
SET IMAGE_NAME=dreamlife-webapp
SET TAG=latest
SET DOCKERHUB_USERNAME=tuusuario

echo Publicando la imagen %IMAGE_NAME%:%TAG% en Docker Hub...

REM Hacer login en Docker Hub (pedirá contraseña)
docker login -u %DOCKERHUB_USERNAME%

REM Etiquetar la imagen para Docker Hub
docker tag %IMAGE_NAME%:%TAG% %DOCKERHUB_USERNAME%/%IMAGE_NAME%:%TAG%

REM Subir la imagen a Docker Hub
docker push %DOCKERHUB_USERNAME%/%IMAGE_NAME%:%TAG%

echo Imagen publicada correctamente en Docker Hub: %DOCKERHUB_USERNAME%/%IMAGE_NAME%:%TAG%