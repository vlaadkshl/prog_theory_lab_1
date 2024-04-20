package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.TeacherData;

public interface TeacherRepository extends JpaRepository<TeacherData, Long> {
    boolean existsByName(String name);
}