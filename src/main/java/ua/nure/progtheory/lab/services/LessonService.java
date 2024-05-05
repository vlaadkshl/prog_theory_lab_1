package ua.nure.progtheory.lab.services;

import ua.nure.progtheory.lab.business.Lesson;

import java.util.List;

public interface LessonService extends ResourceReceiver<Lesson>, ResourceSaver<Lesson>, ResourceRemover<Lesson> {

    List<Lesson> getByGroup(Long groupId);

    List<Lesson> getByTeacher(Long teacherId);

    List<Lesson> getLessonsBySubject(Long subjectId);
}
