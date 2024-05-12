package ua.nure.progtheory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.entity.ReadDataEntity;
import ua.nure.progtheory.kafka.KafkaMessage;
import ua.nure.progtheory.repositories.ReadDataEntityRepository;

@Service
@RequiredArgsConstructor
public class ReadDataAuditor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ReadDataEntityRepository readDataEntityRepository;

    @KafkaListener(topics = "read_data", groupId = "consumer-group-1")
    public void auditReadData(String message) throws JsonProcessingException {
        KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
        ReadDataEntity readDataEntity = ReadDataEntity.builder()
                .tableName(kafkaMessage.tableName())
                .dataJson(kafkaMessage.auditData())
                .queriedAt(kafkaMessage.timestamp())
                .build();
        readDataEntityRepository.save(readDataEntity);
        System.out.println("Someone read data from DB: " + message);
    }
}
