package com.example.multipleConsumerDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

@SpringBootApplication
public class MultipleConsumerDemoApplication {

	private static final String TOPIC_NAME = "my-topic";
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";

	public static void main(String[] args) {

		// create producer
		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

		// create processor thread
		// Read input from console and send to Kafka
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Producer Message: ");
		while (scanner.hasNextLine()) {
			String message = scanner.nextLine();
			ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);
			producer.send(record);
			if (message.isEmpty()) {
				break;
			}
		}
		scanner.close();


		// create consumer group 1
		Properties consumerGroup1Props = new Properties();
		consumerGroup1Props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		consumerGroup1Props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		consumerGroup1Props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerGroup1Props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		KafkaConsumer<String, String> consumerGroup1 = new KafkaConsumer<>(consumerGroup1Props);
		consumerGroup1.subscribe(Collections.singletonList(TOPIC_NAME));

		// create consumer group 2
		Properties consumerGroup2Props = new Properties();
		consumerGroup2Props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		consumerGroup2Props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-2");
		consumerGroup2Props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerGroup2Props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		KafkaConsumer<String, String> consumerGroup2 = new KafkaConsumer<>(consumerGroup2Props);
		consumerGroup2.subscribe(Collections.singletonList(TOPIC_NAME));

		// create listener thread
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(() -> {

				ConsumerRecords<String, String> records = consumerGroup1.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("MESSAGES IN CONSUMER GROUP 1: " + record.value());
				}


		});

		// create listener thread

		executorService.execute(() -> {

				ConsumerRecords<String, String> records = consumerGroup2.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("MESSAGES IN CONSUMER GROUP 2: " + record.value());
				}


		});
		executorService.shutdown();

		// Close producer
		producer.close();
		// Close consumers
		consumerGroup1.close();
		consumerGroup2.close();

	}

}
