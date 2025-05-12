#!/bin/bash

set -e # Exit immediately if a command exits with a non-zero status

cd ../../
#echo "Checking out main branch"
#git checkout main
echo "Building and pushing Docker images"

echo "Building user-service"
cd user-service
./mvnw clean package
docker build -f Dockerfile  -t tyaremenko949/user-service .
docker tag tyaremenko949/user-service tyaremenko949/user-service:latest
cd ../

echo "Building analytics-service"
cd analytics-service
./mvnw clean package
docker build -f Dockerfile  -t tyaremenko949/analytics-service .
docker tag tyaremenko949/analytics-service tyaremenko949/analytics-service:latest
cd ../

echo "Building billing-service"
cd billing-service
./mvnw clean package
docker build -f Dockerfile  -t tyaremenko949/billing-service .
docker tag tyaremenko949/billing-service tyaremenko949/billing-service:latest
cd ../

echo "Building auth-service"
cd auth-service
./mvnw clean package
docker build -f Dockerfile  -t tyaremenko949/auth-service .
docker tag tyaremenko949/auth-service tyaremenko949/auth-service:latest
cd ../

echo "Building gateway"
cd gateway
./mvnw clean package
docker build -f Dockerfile  -t tyaremenko949/gateway .
docker tag tyaremenko949/gateway tyaremenko949/gateway:latest
cd ../

echo "Pushing images"
#docker push tyaremenko949/user-service:latest
#docker push tyaremenko949/analytics-service:latest
#docker push tyaremenko949/billing-service:latest
#docker push tyaremenko949/auth-service:latest
#docker push tyaremenko949/gateway:latest
