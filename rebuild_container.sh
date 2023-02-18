#!/bin/sh
docker stop $1
docker rm $1
docker rmi $1
mvn clean install
docker-compose up -d
