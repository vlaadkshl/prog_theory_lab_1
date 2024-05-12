package ua.nure.progtheory.kafka;

import java.sql.Timestamp;

public record KafkaMessage(String tableName, String auditData, Timestamp timestamp) {
}
