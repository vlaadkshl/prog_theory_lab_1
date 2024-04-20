package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectData, Long> {
    Boolean existsByNameAndTeachers(String name, List<TeacherData> teachers);
}