package ua.nure.progtheory.lab.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.data.LessonData;

@Component
@RequiredArgsConstructor
public class LessonConverter {

    private final GroupConverter groupConverter;

    private final TeacherConverter teacherConverter;

    private final SubjectConverter subjectConverter;


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
