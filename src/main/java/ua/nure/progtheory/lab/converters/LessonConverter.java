package ua.nure.progtheory.lab.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.data.GroupData;
import ua.nure.progtheory.lab.data.LessonData;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;

@Component
@RequiredArgsConstructor
public class LessonConverter implements Converter<Lesson, LessonData> {

    private final Converter<Group, GroupData> groupConverter;

    private final Converter<Teacher, TeacherData> teacherConverter;

    private final Converter<Subject, SubjectData> subjectConverter;

    @Override
    public Lesson fromData(LessonData data) {
        if (data == null) {
            return null;
        }

        return Lesson.builder()
                .id(data.getId())
                .teacher(teacherConverter.fromData(data.getTeacher()))
                .subject(subjectConverter.fromData(data.getSubject()))
                .group(groupConverter.fromData(data.getGroup()))
                .build();
    }

    @Override
    public LessonData toData(Lesson group) {
        if (group == null) {
            return null;
        }

        return LessonData.builder()
                .id(group.getId())
                .teacher(teacherConverter.toData(group.getTeacher()))
                .subject(subjectConverter.toData(group.getSubject()))
                .group(groupConverter.toData(group.getGroup()))
                .build();
    }
}
