#!/bin/bash
# Script para publicar la imagen Docker en Docker Hub

# Configuración
IMAGE_NAME="dreamlife-webapp"
TAG="latest"
DOCKERHUB_USERNAME="tuusuario"  # Reemplaza con tu usuario de Docker Hub

echo "Publicando la imagen ${IMAGE_NAME}:${TAG} en Docker Hub..."

# Hacer login en Docker Hub (pedirá contraseña)
docker login -u ${DOCKERHUB_USERNAME}

# Etiquetar la imagen para Docker Hub
docker tag ${IMAGE_NAME}:${TAG} ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${TAG}

# Subir la imagen a Docker Hub
docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${TAG}

echo "Imagen publicada correctamente en Docker Hub: ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${TAG}"