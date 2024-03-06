package com.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafKaConsumer1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer1.class);

    @KafkaListener(topics = AppConstants.TOPIC_NAME,
            groupId = AppConstants.GROUP_ID1)
    public void consume(String message){
        LOGGER.info(String.format("Message received -> %s", message));
        System.out.println("Message recived By consumer1 "+message);
    }
}