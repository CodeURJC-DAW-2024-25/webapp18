# Script para crear la imagen Docker de la aplicación

# Nombre de la imagen y tag
$IMAGE_NAME = "dreamlife-webapp"
$TAG = "latest"

Write-Host "Construyendo la imagen Docker ${IMAGE_NAME}:${TAG}..."

# Navegar al directorio raíz del proyecto (asumiendo que estamos en la carpeta docker)
cd ..

# Construir la imagen utilizando el Dockerfile en el directorio docker
docker build -t ${IMAGE_NAME}:${TAG} -f docker/Dockerfile .

Write-Host "Imagen construida correctamente: ${IMAGE_NAME}:${TAG}"