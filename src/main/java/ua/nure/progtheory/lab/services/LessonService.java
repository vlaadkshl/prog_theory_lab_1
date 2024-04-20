package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.converters.LessonConverter;
import ua.nure.progtheory.lab.data.LessonData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.repositories.LessonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    private final LessonConverter lessonConverter;

    public List<Lesson> getAllLessons() {
        var lessonsData = lessonRepository.findAll();

        if (lessonsData.isEmpty()) {
            throw new ResourceNotFoundException("No lessons found");
        }

        return lessonsData.stream()
                .map(lessonConverter::fromData)
                .toList();
    }

    public Lesson getLesson(Long lessonId) {
        var lessonData = lessonRepository.findById(lessonId).orElse(null);

        if (lessonData == null) {
            throw new ResourceNotFoundException("Lesson is not found");
        }

        return lessonConverter.fromData(lessonData);
    }

    public List<Lesson> getLessonsByGroup(Long groupId) {
        var lessonsData = lessonRepository.findByGroupId(groupId);

        if (lessonsData.isEmpty()) {
            throw new ResourceNotFoundException("No lessons found for group with id " + groupId);
        }

        return lessonsData.stream()
                .map(lessonConverter::fromData)
                .toList();
    }

    public List<Lesson> getLessonsByTeacher(Long teacherId) {
        var lessonsData = lessonRepository.findByTeacherId(teacherId);

        if (lessonsData.isEmpty()) {
            throw new ResourceNotFoundException("No lessons found for teacher with id " + teacherId);
        }

        return lessonsData.stream()
                .map(lessonConverter::fromData)
                .toList();
    }

    public List<Lesson> getLessonsBySubject(Long subjectId) {
        var lessonsData = lessonRepository.findBySubjectId(subjectId);

        if (lessonsData.isEmpty()) {
            throw new ResourceNotFoundException("No lessons found for subject with id " + subjectId);
        }

        return lessonsData.stream()
                .map(lessonConverter::fromData)
                .toList();
    }

    public Lesson addLesson(Lesson lesson) {
        LessonData lessonData = lessonConverter.toData(lesson);

        if (lessonRepository.existsByTeacherIdAndSubjectIdAndGroupId(
                lessonData.getTeacher().getId(),
                lessonData.getSubject().getId(),
                lessonData.getGroup().getId())) {
            throw new DbRecordAlreadyExistsException(
                    "Lesson with teacher " + lessonData.getTeacher().getName()
                            + ", subject " + lessonData.getSubject().getName()
                            + " and group " + lessonData.getGroup().getName()
                            + " already exists"
            );
        }

        try {
            lessonData = lessonRepository.save(lessonData);
            return lessonConverter.fromData(lessonData);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add lesson due to data integrity violation", ex);
        }
    }

    public void deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson is not found");
        }

        lessonRepository.deleteById(lessonId);
    }
}
