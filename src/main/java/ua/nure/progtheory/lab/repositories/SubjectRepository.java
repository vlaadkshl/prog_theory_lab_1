package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.Subject;
import ua.nure.progtheory.lab.data.Teacher;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Boolean existsByNameAndTeachers(String name, List<Teacher> teachers);
}