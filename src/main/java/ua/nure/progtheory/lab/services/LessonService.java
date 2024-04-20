package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.data.Lesson;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.LessonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }

    public List<Lesson> getLessonsByGroup(Long groupId) {
        return lessonRepository.findByGroupId(groupId);
    }

    public List<Lesson> getLessonsByTeacher(Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId);
    }

    public List<Lesson> getLessonsBySubject(Long subjectId) {
        return lessonRepository.findBySubjectId(subjectId);
    }

    public Lesson addLesson(Lesson lesson) {
        if (lessonRepository.existsByTeacherIdAndSubjectIdAndGroupId(
                lesson.getTeacher().getId(), lesson.getSubject().getId(), lesson.getGroup().getId())) {
            throw new DbRecordAlreadyExistsException(
                    "Lesson with teacher " + lesson.getTeacher().getName()
                            + ", subject " + lesson.getSubject().getName()
                            + " and group " + lesson.getGroup().getName()
                            + " already exists"
            );
        }

        try {
            return lessonRepository.save(lesson);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add lesson due to data integrity violation", ex);
        }
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
