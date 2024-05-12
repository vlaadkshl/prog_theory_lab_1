package ua.nure.progtheory.services.audition;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuditorService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private void sendMessage(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }

    private record AuditData(String tableName, String auditData, Timestamp timestamp) {
    }

    public void auditReading(String data, String entityName) {
        audit(data, entityName, "read_data");
    }

    public void auditUpdating(Object data, String entityName) {
        try {
            audit(objectMapper.writeValueAsString(data), entityName, "update_data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void auditCreation(Object data, String entityName) {
        try {
            audit(objectMapper.writeValueAsString(data), entityName, "create_data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void auditDeleting(Object data, String entityName) {
        try {
            audit(objectMapper.writeValueAsString(data), entityName, "delete_data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void audit(String data, String entityName, String topicName) {
        try {
            AuditData auditData = new AuditData(entityName, data, new Timestamp(System.currentTimeMillis()));
            sendMessage(topicName, objectMapper.writeValueAsString(auditData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
