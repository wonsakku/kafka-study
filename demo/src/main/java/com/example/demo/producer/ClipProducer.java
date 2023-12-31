package com.example.demo.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ClipProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RoutingKafkaTemplate routingKafkaTemplate;
    private final ReplyingKafkaTemplate replyingKafkaTemplate;

    public ClipProducer(KafkaTemplate kafkaTemplate, RoutingKafkaTemplate routingKafkaTemplate, ReplyingKafkaTemplate replyingKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.routingKafkaTemplate = routingKafkaTemplate;
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public void async(String topic, String message){
        final ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        future.addCallback(new KafkaSendCallback<>(){

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Success to send message");
            }

            @Override
            public void onFailure(KafkaProducerException ex) {
                final ProducerRecord<Object, Object> record = ex.getFailedProducerRecord();
                System.out.println("Fail to send message, recode=" + record);
            }
        });
    }

    public void sync(String topic, String message){
        final ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        try {
            System.out.println("Success to send sync message");
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void routingSend(String topic, String message){
        routingKafkaTemplate.send(topic, message);
    }

    public void routingSendBytes(String topic, byte[] message){
        routingKafkaTemplate.send(topic, message);
    }

    public void replyingSend(String topic, String message) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        final RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(record);

        final ConsumerRecord<String, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        System.out.println(consumerRecord.value());
    }

}
