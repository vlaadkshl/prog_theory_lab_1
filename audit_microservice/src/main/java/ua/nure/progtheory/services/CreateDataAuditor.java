package ua.nure.progtheory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.entity.CreateDataEntity;
import ua.nure.progtheory.kafka.KafkaMessage;
import ua.nure.progtheory.repositories.CreateDataEntityRepository;

@Service
@RequiredArgsConstructor
public class CreateDataAuditor {

    private final CreateDataEntityRepository createDataEntityRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "create_data", groupId = "consumer-group-1")
    public void auditCreateData(String message) throws JsonProcessingException {
        KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
        CreateDataEntity createDataEntity = CreateDataEntity.builder()
                .tableName(kafkaMessage.tableName())
                .dataJson(kafkaMessage.auditData())
                .queriedAt(kafkaMessage.timestamp())
                .build();
        createDataEntityRepository.save(createDataEntity);
        System.out.println("Someone created data: " + message);
    }
}
