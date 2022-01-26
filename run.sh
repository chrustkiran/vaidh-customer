docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker rmi $(docker images -a -q)

 chmod +x gradlew
./gradlew clean build

DOCKER_BUILDKIT=1 docker build -t vaidh/customer .

sudo docker run -e PSQL_DB_SERVER='localhost' -e PSQL_DB_NAME='vaidhdb' -e PSQL_DB_USERNAME='vaidh' -e PSQL_DB_PW='Vaidh123' -e JWT_SECRET='abcd123abcvaidh' -e FIREBASE_URL='https://vaidh-d7457-default-rtdb.firebaseio.com' -e VAIDH_MAIL_PW='Vaidh123' -e VAIDH_MAIL_ID='vaidh.notify@gmail.com' -p 8080:8080 --net="host" --restart always vaidh/customer
