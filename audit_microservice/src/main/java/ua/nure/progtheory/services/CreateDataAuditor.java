package ua.nure.progtheory.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CreateDataAuditor {

    @KafkaListener(topics = "create_data", groupId = "consumer-group-1")
    public void auditCreateData(String message) {
        System.out.println("Someone created data: " + message);
    }
}
