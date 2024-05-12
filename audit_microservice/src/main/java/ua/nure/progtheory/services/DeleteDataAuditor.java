package ua.nure.progtheory.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeleteDataAuditor {

    @KafkaListener(topics = "delete_data", groupId = "consumer-group-1")
    public void auditDeleteData(String message) {
        System.out.println("Someone deleted data: " + message);
    }
}
