package com.finledger.finledger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;



    public void publishTransactionEvent(String message) {
        kafkaTemplate.send("transactions.completed", message);
    }
}
