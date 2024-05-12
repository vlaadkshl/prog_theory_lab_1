package ua.nure.progtheory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.entity.UpdateDataEntity;
import ua.nure.progtheory.kafka.KafkaMessage;
import ua.nure.progtheory.repositories.UpdateDataEntityRepository;

@Service
@RequiredArgsConstructor
public class UpdateDataAuditor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UpdateDataEntityRepository updateDataEntityRepository;

    @KafkaListener(topics = "update_data", groupId = "consumer-group-1")
    public void auditUpdateData(String message) throws JsonProcessingException {
        KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
        UpdateDataEntity updateDataEntity = UpdateDataEntity.builder()
                .tableName(kafkaMessage.tableName())
                .dataJson(kafkaMessage.auditData())
                .queriedAt(kafkaMessage.timestamp())
                .build();
        updateDataEntityRepository.save(updateDataEntity);
        System.out.println("Someone updated data: " + message);
    }
}
