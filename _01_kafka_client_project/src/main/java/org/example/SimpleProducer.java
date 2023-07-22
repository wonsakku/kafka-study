package org.example;

import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SimpleProducer {

    private final static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // kafka-producer-custom-partitioner
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);


        final KafkaProducer producer = new KafkaProducer<>(configs);

        final String messageValue = "hello kafka-client";
//        final ProducerRecord<Object, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
        // key-value
//        final ProducerRecord<Object, String> record = new ProducerRecord<>(TOPIC_NAME, "Pangyo", "23");
        // exact-partition
        int partitionNo = 0;
        final ProducerRecord<Object, String> record = new ProducerRecord<>(TOPIC_NAME, partitionNo, "messageKey", "messageValue");

//        final Object metadata = producer.send(record).get();
//        logger.info("{}", record);
//        logger.info(metadata.toString());
//        producer.flush();
//        producer.close();

        // async-callback
        producer.send(record, new ProducerCallback());
//        producer.flush();
//        producer.close();
    }
}










