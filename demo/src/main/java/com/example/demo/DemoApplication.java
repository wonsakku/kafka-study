package com.example.demo;

import com.example.demo.producer.ClipProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	public ApplicationRunner runner(KafkaTemplate<String, String> kafkaTemplate){
//		return args -> {
//			kafkaTemplate.send("quickstart-events", "hello-world");
//		};
//	}

//	@Bean
//	public ApplicationRunner runner(AdminClient adminClient){
//		return args -> {
//			final Map<String, TopicListing> topics = adminClient.listTopics().namesToListings().get();
//
//			for (String topicName : topics.keySet()) {
//				final TopicListing topicListing = topics.get(topicName);
//				System.out.println(topicListing);
//
//				final Map<String, TopicDescription> description = adminClient.describeTopics(Collections.singleton(topicName)).all().get();
//				System.out.println(description);
//
//				// 삭제하다 에러남...
//				adminClient.deleteTopics(Collections.singleton(topicName));
//			}
//
//		};
//	}

	@Bean
	public ApplicationRunner runner(ClipProducer clipProducer){
		return args -> {
			clipProducer.async("clip3", "Hello, Clip3-async");
//			Thread.sleep(1000l);
			clipProducer.sync("clip3", "Hello, Clip3-sync");
			clipProducer.routingSend("clip3", "Hello, Clip3-routing");
			clipProducer.routingSendBytes("clip3-bytes", "Hello, Clip3-bytes".getBytes(StandardCharsets.UTF_8));
//			clipProducer.routingSend("clip3-bytes", "Hello, Clip3-bytes");
			clipProducer.replyingSend("clip3-request", "Ping Clip3");
		};
	}





}
