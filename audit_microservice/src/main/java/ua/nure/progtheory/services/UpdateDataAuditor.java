package ua.nure.progtheory.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UpdateDataAuditor {

    @KafkaListener(topics = "update_data", groupId = "consumer-group-1")
    public void auditUpdateData(String message) {
        System.out.println("Someone updated data: " + message);
    }
}
