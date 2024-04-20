package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.data.Lesson;
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
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
