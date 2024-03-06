package com.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig
{
    @Bean
    public NewTopic javaguidesTopic()
    {
        return TopicBuilder.name("javaguides").build();
    }

    //creating topic1
    @Bean
    public NewTopic javaguidesTopic1()
    {
        return TopicBuilder.name("javaguides1").build();
    }
}
