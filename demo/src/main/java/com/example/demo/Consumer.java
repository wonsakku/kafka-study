package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//    @KafkaListener(id = "fast-campus-id", topics = "quickstart-events") // annotation 이 있어야 컨슈머 역할을 함
//    public void listen(String message){
//        System.out.println("=======================");
//        System.out.println(message);
//        System.out.println("=======================");
//    }
}
