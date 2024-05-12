package ua.nure.progtheory.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "teacher")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany(mappedBy = "teachers")
    private List<SubjectData> subjects = new ArrayList<>();
}