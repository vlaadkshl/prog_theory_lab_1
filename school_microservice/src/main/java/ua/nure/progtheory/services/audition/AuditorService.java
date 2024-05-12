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

    public void auditReading(Object entity, String entityName) {
        try {
            auditReading(objectMapper.writeValueAsString(entity), entityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void auditReading(String data, String entityName) {
        try {
            AuditData auditData = new AuditData(entityName, data, new Timestamp(System.currentTimeMillis()));
            sendMessage("read_data", objectMapper.writeValueAsString(auditData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
