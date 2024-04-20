package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}