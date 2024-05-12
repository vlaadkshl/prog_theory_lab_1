package ua.nure.progtheory.converters;

import org.springframework.stereotype.Component;
import ua.nure.progtheory.business.Teacher;
import ua.nure.progtheory.data.TeacherData;

@Component
public class TeacherConverter implements Converter<Teacher, TeacherData> {

    @Override
    public Teacher fromData(TeacherData data) {
        if (data == null) {
            return null;
        }

        return Teacher.builder()
                .id(data.getId())
                .name(data.getName())
                .build();
    }

    @Override
    public TeacherData toData(Teacher group) {
        if (group == null) {
            return null;
        }

        return TeacherData.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}
