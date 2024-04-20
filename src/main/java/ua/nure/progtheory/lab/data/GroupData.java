package ua.nure.progtheory.lab.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "group_table")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;
}