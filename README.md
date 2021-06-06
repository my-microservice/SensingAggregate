# Running Axon Server

To run Axon Server in Docker you can use the image provided on Docker Hub

```cmd
$ docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver
...some container id...
```



# Kafka-Docker repo clone
```cmd
$ git clone https://github.com/wurstmeister/kafka-docker
```
## docker-compose.yml 파일 수정
```cmd
...
KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
...
```
docker-compose 실행
```cmd 
$ docker-compose -f docker-compose-single-broker.yml up -d
```
