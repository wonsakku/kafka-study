package com.example.demo;

import com.example.demo.model.Animal;
import com.example.demo.producer.ClipProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;


@SpringBootApplication
public class DemoApplication {

	private final Logger log  = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(ClipProducer clipProducer){
		return args -> {
			clipProducer.async("clip4-listener", "Hello, Clip4 Listener.");
			clipProducer.async("clip4-animal", new Animal("puppy", 15));
		};
	}

//	@Bean
//	public ApplicationRunner runner(ClipProducer clipProducer,
//									KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer){
//		return args -> {
//			clipProducer.async("clip4", "Hello, Clip4 Container.");
//
//			kafkaMessageListenerContainer.start();
//
//			Thread.sleep(1_000L);
//
//			log.info("----- pause -----");
//			kafkaMessageListenerContainer.pause();
//			Thread.sleep(5_000L);
//
//			clipProducer.async("clip4", "Hello, Secondly Clip4 Container.");
//
//			log.info("----- resume -----");
//			kafkaMessageListenerContainer.resume();
//			Thread.sleep(1_000L);
//
//			log.info("----- stop -----");
//			kafkaMessageListenerContainer.stop();
//		};
//	}




}
