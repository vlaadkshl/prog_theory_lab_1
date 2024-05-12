package ua.nure.progtheory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.entity.DeleteDataEntity;
import ua.nure.progtheory.kafka.KafkaMessage;
import ua.nure.progtheory.repositories.DeleteDataEntityRepository;

@Service
@RequiredArgsConstructor
public class DeleteDataAuditor {

    private final DeleteDataEntityRepository deleteDataEntityRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "delete_data", groupId = "consumer-group-1")
    public void auditDeleteData(String message) throws JsonProcessingException {
        KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
        DeleteDataEntity deleteDataEntity = DeleteDataEntity.builder()
                .tableName(kafkaMessage.tableName())
                .dataJson(kafkaMessage.auditData())
                .queriedAt(kafkaMessage.timestamp())
                .build();
        deleteDataEntityRepository.save(deleteDataEntity);
        System.out.println("Someone deleted data: " + message);
    }
}
