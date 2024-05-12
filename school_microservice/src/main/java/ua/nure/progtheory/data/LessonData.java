package ua.nure.progtheory.data;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "lesson")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherData teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectData subject;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupData group;
}