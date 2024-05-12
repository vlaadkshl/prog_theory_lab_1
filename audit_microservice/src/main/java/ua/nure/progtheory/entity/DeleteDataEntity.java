package ua.nure.progtheory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "delete_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "data_json")
    private String dataJson;

    @Column(name = "queried_at")
    private Timestamp queriedAt;
}
