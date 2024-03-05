package com.deliveryBoy.deliveryBoy.service;

import com.deliveryBoy.deliveryBoy.config.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    //for produce message we use KafkaTemplate
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    public boolean updateLocation(String location) {
        try {
            kafkaTemplate.send(AppConstant.LOCATION_TOPIC_NAME, location);
            logger.info("Message Produced - Location Updated");
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while producing message: {}", e.getMessage());
            return false;
        }
    }
}
