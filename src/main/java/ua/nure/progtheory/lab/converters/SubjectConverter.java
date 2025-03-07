package ua.nure.progtheory.lab.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;

@Component
@RequiredArgsConstructor
public class SubjectConverter implements Converter<Subject, SubjectData> {

    private final Converter<Teacher, TeacherData> teacherConverter;

    @Override
    public Subject fromData(SubjectData data) {
        if (data == null) {
            return null;
        }

        return Subject.builder()
                .id(data.getId())
                .name(data.getName())
                .teachers(data.getTeachers().stream().map(teacherConverter::fromData).toList())
                .build();
    }

    @Override
    public SubjectData toData(Subject group) {
        if (group == null) {
            return null;
        }

        return SubjectData.builder()
                .id(group.getId())
                .name(group.getName())
                .teachers(group.getTeachers().stream().map(teacherConverter::toData).toList())
                .build();
    }
}
