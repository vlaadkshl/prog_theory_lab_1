package ua.nure.progtheory.lab.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.data.LessonData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class LessonConverterTest {

    @InjectMocks
    private LessonConverter lessonConverter;

    @Mock
    private GroupConverter groupConverter;

    @Mock
    private TeacherConverter teacherConverter;

    @Mock
    private SubjectConverter subjectConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fromDataShouldReturnLessonWhenDataIsNotNull() {
        long id = 1L;

        LessonData data = LessonData.builder().id(id).build();

        when(groupConverter.fromData(data.getGroup())).thenReturn(null);
        when(teacherConverter.fromData(data.getTeacher())).thenReturn(null);
        when(subjectConverter.fromData(data.getSubject())).thenReturn(null);

        Lesson result = lessonConverter.fromData(data);

        assertEquals(id, result.getId());
    }

    @Test
    public void fromDataShouldReturnNullWhenDataIsNull() {
        Lesson result = lessonConverter.fromData(null);

        assertNull(result);
    }

    @Test
    public void toDataShouldReturnLessonDataWhenLessonIsNotNull() {
        long id = 1L;

        Lesson lesson = Lesson.builder().id(id).build();
        
        when(groupConverter.toData(lesson.getGroup())).thenReturn(null);
        when(teacherConverter.toData(lesson.getTeacher())).thenReturn(null);
        when(subjectConverter.toData(lesson.getSubject())).thenReturn(null);

        LessonData result = lessonConverter.toData(lesson);

        assertEquals(id, result.getId());
    }

    @Test
    public void toDataShouldReturnNullWhenLessonIsNull() {
        LessonData result = lessonConverter.toData(null);

        assertNull(result);
    }
}