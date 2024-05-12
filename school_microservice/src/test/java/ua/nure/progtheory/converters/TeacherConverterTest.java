package ua.nure.progtheory.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ua.nure.progtheory.business.Teacher;
import ua.nure.progtheory.data.TeacherData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TeacherConverterTest {

    @InjectMocks
    private TeacherConverter teacherConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fromDataShouldReturnTeacherWhenDataIsNotNull() {
        long id = 1L;
        String testTeacher = "Test Teacher";

        TeacherData data = TeacherData.builder().id(id).name(testTeacher).build();

        Teacher result = teacherConverter.fromData(data);

        assertEquals(id, result.getId());
        assertEquals(testTeacher, result.getName());
    }

    @Test
    public void fromDataShouldReturnNullWhenDataIsNull() {
        Teacher result = teacherConverter.fromData(null);

        assertNull(result);
    }

    @Test
    public void toDataShouldReturnTeacherDataWhenTeacherIsNotNull() {
        long id = 1L;
        String testTeacher = "Test Teacher";

        Teacher teacher = Teacher.builder().id(id).name(testTeacher).build();

        TeacherData result = teacherConverter.toData(teacher);

        assertEquals(id, result.getId());
        assertEquals(testTeacher, result.getName());
    }

    @Test
    public void toDataShouldReturnNullWhenTeacherIsNull() {
        TeacherData result = teacherConverter.toData(null);

        assertNull(result);
    }
}
