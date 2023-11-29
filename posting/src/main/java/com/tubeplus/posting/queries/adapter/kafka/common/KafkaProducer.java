package com.tubeplus.posting.queries.adapter.kafka.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topic3.name}")
    private String topic3;

    @SuppressWarnings("unused")
    public void sendMessage(String kafkaTopic, String message) {
        System.out.println("kafka message send:" + message);

        kafkaTopic = kafkaTopic.trim();
        try {
            kafkaTemplate.send(kafkaTopic.trim(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
