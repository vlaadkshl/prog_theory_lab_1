package ua.nure.progtheory.lab.converters;

import org.springframework.stereotype.Component;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.data.TeacherData;

@Component
public class TeacherConverter {

    public Teacher fromData(TeacherData data) {
        if (data == null) {
            return null;
        }

        return Teacher.builder()
                .id(data.getId())
                .name(data.getName())
                .build();
    }

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
