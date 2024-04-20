package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}