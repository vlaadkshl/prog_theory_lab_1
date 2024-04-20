package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.LessonData;

import java.util.List;

public interface LessonRepository extends JpaRepository<LessonData, Long> {
    List<LessonData> findByGroupId(Long groupId);

    List<LessonData> findByTeacherId(Long teacherId);

    List<LessonData> findBySubjectId(Long subjectId);

    Boolean existsByTeacherIdAndSubjectIdAndGroupId(Long teacherId, Long subjectId, Long groupId);
}