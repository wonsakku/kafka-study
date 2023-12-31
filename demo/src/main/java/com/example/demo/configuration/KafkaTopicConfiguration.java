package com.example.demo.configuration;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {

//    @Bean
//    public NewTopic clip2(){
//        return TopicBuilder
//                .name("clip2")
//                .build();
//    }

//    @Bean
//    public KafkaAdmin.NewTopics clip2s(){
//        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("clip2-part1").build(),
//                TopicBuilder.name("clip2-part2")
//                        .partitions(3)
//                        .replicas(1)
//                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000 * 60 * 60))
//                        .build()
//        );
//    }


//    @Bean
//    public AdminClient adminClient(KafkaAdmin kafkaAdmin){
//        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
//    }


    @Bean
    public KafkaAdmin.NewTopics clip2s(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("clip3").build()
                ,TopicBuilder.name("clip3-bytes").build()
        );
    }

}
