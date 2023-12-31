# Part3. Spring for Apache Kafka

___

## Ch 02. Spring for Kafka 기본  

<br>

### (01) Quick Start  

<br>

1. apache zookeeper zoo_sample.cfg 를 zoo.cfg 로 파일명 변경  

2. 로그를 보기 위해 kafka foreground로 실행  

```sh
bin/zkServer.sh start-foreground
```

3. kafka 실행  

```sh
./bin/kafka-server-start.sh config/server.properties
```

4. topic 생성   

```sh
./bin/kafka-topics.sh --create --topic quckstart-events --bootstrap-server localhost:9092
```

5. 