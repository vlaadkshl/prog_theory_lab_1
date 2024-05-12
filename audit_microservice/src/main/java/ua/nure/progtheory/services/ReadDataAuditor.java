package ua.nure.progtheory.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReadDataAuditor {

    @KafkaListener(topics = "read_data", groupId = "consumer-group-1")
    public void auditReadData(String message) {
        System.out.println("Someone read data from DB: " + message);
    }
}
