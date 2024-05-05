package ua.nure.progtheory.lab.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.converters.LessonConverter;
import ua.nure.progtheory.lab.data.GroupData;
import ua.nure.progtheory.lab.data.LessonData;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.repositories.LessonRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LessonServiceImplTest {

    @Mock
    LessonRepository lessonRepository;

    @Mock
    LessonConverter lessonConverter;

    @InjectMocks
    LessonServiceImpl lessonServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLessonsReturnsAllLessons() {
        LessonData lessonData1 = new LessonData();
        LessonData lessonData2 = new LessonData();
        Lesson lesson1 = Lesson.builder().build();
        Lesson lesson2 = Lesson.builder().build();

        when(lessonRepository.findAll()).thenReturn(Arrays.asList(lessonData1, lessonData2));
        when(lessonConverter.fromData(lessonData1)).thenReturn(lesson1);
        when(lessonConverter.fromData(lessonData2)).thenReturn(lesson2);

        assertEquals(Arrays.asList(lesson1, lesson2), lessonServiceImpl.getAll());
    }

    @Test
    void getLessonReturnsLessonIfExists() {
        LessonData lessonData = new LessonData();
        Lesson lesson = Lesson.builder().build();

        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lessonData));
        when(lessonConverter.fromData(lessonData)).thenReturn(lesson);

        assertEquals(lesson, lessonServiceImpl.get(1L));
    }

    @Test
    void getLessonReturnsNullIfNotExists() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.get(1L));
    }

    @Test
    void addLessonThrowsExceptionIfLessonExists() {
        Lesson lesson = Lesson.builder()
                .teacher(Teacher.builder().id(1L).build())
                .subject(Subject.builder().id(1L).build())
                .group(Group.builder().id(1L).build())
                .build();
        LessonData lessonData = LessonData.builder()
                .teacher(TeacherData.builder().id(1L).build())
                .subject(SubjectData.builder().id(1L).build())
                .group(GroupData.builder().id(1L).build())
                .build();

        when(lessonConverter.toData(any(Lesson.class))).thenReturn(lessonData);
        when(lessonConverter.fromData(any(LessonData.class))).thenReturn(lesson);
        when(lessonRepository.existsByTeacherIdAndSubjectIdAndGroupId(anyLong(), anyLong(), anyLong())).thenReturn(true);

        assertThrows(DbRecordAlreadyExistsException.class, () -> lessonServiceImpl.save(lesson));
    }

    @Test
    void addLessonReturnsLessonIfNotExists() {
        Lesson lesson = Lesson.builder()
                .teacher(Teacher.builder().id(1L).build())
                .subject(Subject.builder().id(1L).build())
                .group(Group.builder().id(1L).build())
                .build();
        LessonData lessonData = LessonData.builder()
                .teacher(TeacherData.builder().id(1L).build())
                .subject(SubjectData.builder().id(1L).build())
                .group(GroupData.builder().id(1L).build())
                .build();

        when(lessonRepository.existsByTeacherIdAndSubjectIdAndGroupId(anyLong(), anyLong(), anyLong())).thenReturn(false);
        when(lessonConverter.toData(any(Lesson.class))).thenReturn(lessonData);
        when(lessonRepository.save(any(LessonData.class))).thenReturn(lessonData);
        when(lessonConverter.fromData(any(LessonData.class))).thenReturn(lesson);

        assertEquals(lesson, lessonServiceImpl.save(lesson));
    }

    @Test
    void addLessonThrowsExceptionOnDataIntegrityViolation() {
        Lesson lesson = Lesson.builder().build();
        LessonData lessonData = new LessonData();

        when(lessonRepository.existsByTeacherIdAndSubjectIdAndGroupId(anyLong(), anyLong(), anyLong())).thenReturn(false);
        when(lessonConverter.toData(any(Lesson.class))).thenReturn(lessonData);
        when(lessonRepository.save(any(LessonData.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> lessonServiceImpl.save(lesson));
    }

    @Test
    void deleteLessonDeletesLesson() {
        doNothing().when(lessonRepository).deleteById(anyLong());
        when(lessonRepository.existsById(anyLong())).thenReturn(true);

        lessonServiceImpl.delete(1L);

        verify(lessonRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getLessonsByGroupReturnsIfExist() {
        LessonData lessonData = new LessonData();
        Lesson lesson = Lesson.builder().build();

        when(lessonRepository.findByGroupId(anyLong())).thenReturn(Arrays.asList(lessonData));
        when(lessonConverter.fromData(lessonData)).thenReturn(lesson);

        assertEquals(Arrays.asList(lesson), lessonServiceImpl.getByGroup(1L));
    }

    @Test
    void getLessonsByGroupThrowsExceptionIfNoExist() {
        when(lessonRepository.findByGroupId(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.getByGroup(1L));
    }

    @Test
    void getLessonsByTeacherReturnsIfExist() {
        LessonData lessonData = new LessonData();
        Lesson lesson = Lesson.builder().build();

        when(lessonRepository.findByTeacherId(anyLong())).thenReturn(Arrays.asList(lessonData));
        when(lessonConverter.fromData(lessonData)).thenReturn(lesson);

        assertEquals(Arrays.asList(lesson), lessonServiceImpl.getByTeacher(1L));
    }

    @Test
    void getLessonsByTeacherThrowsExceptionIfNoExist() {
        when(lessonRepository.findByTeacherId(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.getByTeacher(1L));
    }

    @Test
    void getLessonsBySubjectReturnsLessonsIfExist() {
        LessonData lessonData = new LessonData();
        Lesson lesson = Lesson.builder().build();

        when(lessonRepository.findBySubjectId(anyLong())).thenReturn(Arrays.asList(lessonData));
        when(lessonConverter.fromData(lessonData)).thenReturn(lesson);

        assertEquals(Arrays.asList(lesson), lessonServiceImpl.getLessonsBySubject(1L));
    }

    @Test
    void getLessonsBySubjectThrowsExceptionIfNoLessonsExist() {
        when(lessonRepository.findBySubjectId(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.getLessonsBySubject(1L));
    }

    @Test
    void deleteLessonDeletesLessonIfExists() {
        doNothing().when(lessonRepository).deleteById(anyLong());
        when(lessonRepository.existsById(anyLong())).thenReturn(true);

        lessonServiceImpl.delete(1L);

        verify(lessonRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteLessonThrowsExceptionIfNotExists() {
        when(lessonRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.delete(1L));
    }
}