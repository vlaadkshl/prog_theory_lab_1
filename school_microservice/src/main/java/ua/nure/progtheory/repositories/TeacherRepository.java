package ua.nure.progtheory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.data.TeacherData;

public interface TeacherRepository extends JpaRepository<TeacherData, Long> {
    boolean existsByName(String name);
}