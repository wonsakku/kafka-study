package com.example.demo.consumer;

import com.example.demo.model.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class ClipConsumer {

    private final Logger logger = LoggerFactory.getLogger(ClipConsumer.class);

    @KafkaListener(id = "clip4-listener-id", topics = "clip4-listener"
//            , concurrency = "2",
//            clientIdPrefix = "listener"
    )
    public void listen(String message
                       ,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp
                       ,@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
                    , ConsumerRecordMetadata metadata){
        logger.info("Listener message : {}", message);
        logger.info("Listener offset : {}", metadata.offset());
        logger.info("timestamp : {}", timestamp);
        logger.info("partition : {}", partition);
    }

    @KafkaListener(id = "clip4-animal-listener", topics = "clip4-animal", containerFactory = "kafkaJsonContainerFactory")
    public void listenAnimal(@Valid Animal animal){
        logger.info("Animal. animal={}", animal);
    }


}
