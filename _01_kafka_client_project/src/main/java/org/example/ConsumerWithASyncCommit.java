package org.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class ConsumerWithASyncCommit {
    private final static Logger logger = LoggerFactory.getLogger(ConsumerWithASyncCommit.class);
    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private final static String GROUP_ID = "test-group";

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

//        final Set<TopicPartition> assignment = consumer.assignment();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("record:{}", record);
            }

            consumer.commitAsync((offsets, e) -> {
                if(e != null)
                    System.err.println("commit failed");
                else
                    System.out.println("Commit succeeded");
                if(e != null)
                    logger.error("Commit failed for offsets {}", offsets, e);
            });
//            consumer.commitAsync(new OffsetCommitCallback() {
//                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
//                    if (e != null)
//                        System.err.println("Commit failed");
//                    else
//                        System.out.println("Commit succeeded");
//                    if (e != null)
//                        logger.error("Commit failed for offsets {}", offsets, e);
//                }
//            });
        }
    }
}
