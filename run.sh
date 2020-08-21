docker-compose stop && docker-compose rm -v

mvn -e clean package -DskipTests

docker-compose up -d --force-recreate --build