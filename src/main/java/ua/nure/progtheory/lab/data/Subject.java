package ua.nure.progtheory.lab.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany
    @JoinTable(name = "teachersubject",
            joinColumns = @JoinColumn(name = "subject_id"))
    private List<Teacher> teachers = new ArrayList<>();

}