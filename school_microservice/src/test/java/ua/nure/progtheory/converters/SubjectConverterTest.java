package ua.nure.progtheory.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.nure.progtheory.business.Subject;
import ua.nure.progtheory.data.SubjectData;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class SubjectConverterTest {

    @InjectMocks
    private SubjectConverter subjectConverter;

    @Mock
    private TeacherConverter teacherConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fromDataShouldReturnSubjectWhenDataIsNotNull() {
        long id = 1L;
        String testSubject = "Test Subject";

        SubjectData data = SubjectData.builder().id(id).name(testSubject).teachers(Collections.emptyList()).build();

        when(teacherConverter.fromData(null)).thenReturn(null);

        Subject result = subjectConverter.fromData(data);

        assertEquals(id, result.getId());
        assertEquals(testSubject, result.getName());
        assertEquals(0, result.getTeachers().size());
    }

    @Test
    public void fromDataShouldReturnNullWhenDataIsNull() {
        Subject result = subjectConverter.fromData(null);

        assertNull(result);
    }

    @Test
    public void toDataShouldReturnSubjectDataWhenSubjectIsNotNull() {
        long id = 1L;
        String testSubject = "Test Subject";

        Subject subject = Subject.builder().id(id).name(testSubject).teachers(Collections.emptyList()).build();

        when(teacherConverter.toData(null)).thenReturn(null);

        SubjectData result = subjectConverter.toData(subject);

        assertEquals(id, result.getId());
        assertEquals(testSubject, result.getName());
        assertEquals(0, result.getTeachers().size());
    }

    @Test
    public void toDataShouldReturnNullWhenSubjectIsNull() {
        SubjectData result = subjectConverter.toData(null);

        assertNull(result);
    }
}