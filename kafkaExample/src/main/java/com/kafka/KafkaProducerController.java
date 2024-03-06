package com.kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController
{
    private KafkaProducer kafkaProducer;

    private  KafkaProducer2 kafkaProducer2;

    public KafkaProducerController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public KafkaProducerController(KafkaProducer2 kafkaProducer2) {
        this.kafkaProducer2 = kafkaProducer2;
    }

    public KafkaProducerController() {}

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to kafka topic");
    }

    @GetMapping("/publish2")
    public ResponseEntity<String> publish2(@RequestParam("message") String message){
        kafkaProducer2.sendMessage(message);
        return ResponseEntity.ok("Message sent to kafka topic");
    }
}
