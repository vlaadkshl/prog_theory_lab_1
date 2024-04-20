package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.data.LessonData;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.LessonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        var lessonsData = lessonRepository.findAll();

        return lessonsData.stream()
                .map(lesson -> {
                    var subject = lesson.getSubject();
                    var teacher = lesson.getTeacher();

                    return Lesson.builder()
                            .id(lesson.getId())
                            .group(lesson.getGroup())
                            .subject(Subject.builder()
                                    .id(subject.getId())
                                    .name(subject.getName())
                                    .build())
                            .teacher(Teacher.builder()
                                    .id(teacher.getId())
                                    .name(teacher.getName())
                                    .build())
                            .build();
                })
                .toList();
    }

    public Lesson getLesson(Long lessonId) {
        var lessonData = lessonRepository.findById(lessonId).orElse(null);

        assert lessonData != null;
        var subject = lessonData.getSubject();
        var teacher = lessonData.getTeacher();

        return Lesson.builder()
                .id(lessonData.getId())
                .group(lessonData.getGroup())
                .subject(Subject.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .build())
                .teacher(Teacher.builder()
                        .id(teacher.getId())
                        .name(teacher.getName())
                        .build())
                .build();
    }

    public List<Lesson> getLessonsByGroup(Long groupId) {
        var lessonsData = lessonRepository.findByGroupId(groupId);

        return lessonsData.stream()
                .map(lesson -> {
                    var subject = lesson.getSubject();
                    var teacher = lesson.getTeacher();

                    return Lesson.builder()
                            .id(lesson.getId())
                            .group(lesson.getGroup())
                            .subject(Subject.builder()
                                    .id(subject.getId())
                                    .name(subject.getName())
                                    .build())
                            .teacher(Teacher.builder()
                                    .id(teacher.getId())
                                    .name(teacher.getName())
                                    .build())
                            .build();
                })
                .toList();
    }

    public List<Lesson> getLessonsByTeacher(Long teacherId) {
        var lessonsData = lessonRepository.findByTeacherId(teacherId);

        return lessonsData.stream()
                .map(lesson -> {
                    var subject = lesson.getSubject();
                    var teacher = lesson.getTeacher();

                    return Lesson.builder()
                            .id(lesson.getId())
                            .group(lesson.getGroup())
                            .subject(Subject.builder()
                                    .id(subject.getId())
                                    .name(subject.getName())
                                    .build())
                            .teacher(Teacher.builder()
                                    .id(teacher.getId())
                                    .name(teacher.getName())
                                    .build())
                            .build();
                })
                .toList();
    }

    public List<Lesson> getLessonsBySubject(Long subjectId) {
        var lessonsData = lessonRepository.findBySubjectId(subjectId);

        return lessonsData.stream()
                .map(lesson -> {
                    var subject = lesson.getSubject();
                    var teacher = lesson.getTeacher();

                    return Lesson.builder()
                            .id(lesson.getId())
                            .group(lesson.getGroup())
                            .subject(Subject.builder()
                                    .id(subject.getId())
                                    .name(subject.getName())
                                    .build())
                            .teacher(Teacher.builder()
                                    .id(teacher.getId())
                                    .name(teacher.getName())
                                    .build())
                            .build();
                })
                .toList();
    }

    public Lesson addLesson(Lesson lesson) {
        SubjectData subjectData = SubjectData.builder()
                .id(lesson.getSubject().getId())
                .name(lesson.getSubject().getName())
                .build();

        TeacherData teacherData = TeacherData.builder()
                .id(lesson.getTeacher().getId())
                .name(lesson.getTeacher().getName())
                .build();

        LessonData lessonData = LessonData.builder()
                .group(lesson.getGroup())
                .subject(subjectData)
                .teacher(teacherData)
                .build();

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

            var subject = lessonData.getSubject();
            var teacher = lessonData.getTeacher();

            return Lesson.builder()
                    .id(lessonData.getId())
                    .group(lessonData.getGroup())
                    .subject(Subject.builder()
                            .id(subject.getId())
                            .name(subject.getName())
                            .build())
                    .teacher(Teacher.builder()
                            .id(teacher.getId())
                            .name(teacher.getName())
                            .build())
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add lesson due to data integrity violation", ex);
        }
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
