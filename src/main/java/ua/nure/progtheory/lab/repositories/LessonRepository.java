package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByGroupId(Long groupId);

    List<Lesson> findByTeacherId(Long teacherId);

    List<Lesson> findBySubjectId(Long subjectId);

    Boolean existsByTeacherIdAndSubjectIdAndGroupId(Long teacherId, Long subjectId, Long groupId);
}