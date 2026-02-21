package com.finledger.finledger.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @KafkaListener(topics = "transactions.completed", groupId = "finledger-group")
    public void consume(String message) {
        System.out.println("Received Event: " + message);

        // Simulate fraud rule
        if (message.contains("100000")) {
            System.out.println("⚠️ High value transaction flagged!");
        }
    }
}

