#!/bin/sh
#docker pull mariadb
#docker run --name course-api-db -e MYSQL_ROOT_PASSWORD=rooty -d mariadb
#docker stack deploy -c stack.yml mariadb
docker-compose -f stack.yml up