package ua.nure.progtheory.lab.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.converters.TeacherConverter;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TeacherServiceImplTest {

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    TeacherConverter teacherConverter;

    @InjectMocks
    TeacherServiceImpl teacherServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTeachersReturnsAllTeachers() {
        TeacherData teacherData1 = new TeacherData();
        TeacherData teacherData2 = new TeacherData();
        Teacher teacher1 = Teacher.builder().build();
        Teacher teacher2 = Teacher.builder().build();

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacherData1, teacherData2));
        when(teacherConverter.fromData(teacherData1)).thenReturn(teacher1);
        when(teacherConverter.fromData(teacherData2)).thenReturn(teacher2);

        assertEquals(Arrays.asList(teacher1, teacher2), teacherServiceImpl.getAll());
    }

    @Test
    void getTeacherReturnsTeacherIfExists() {
        TeacherData teacherData = new TeacherData();
        Teacher teacher = Teacher.builder().build();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacherData));
        when(teacherConverter.fromData(teacherData)).thenReturn(teacher);

        assertEquals(teacher, teacherServiceImpl.get(1L));
    }

    @Test
    void getTeacherReturnsNullIfNotExists() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teacherServiceImpl.get(1L));
    }

    @Test
    void addTeacherThrowsExceptionIfTeacherExists() {
        Teacher teacher = Teacher.builder().id(1L).name("Name").build();
        TeacherData teacherData = TeacherData.builder().id(1L).name("Name").build();

        when(teacherConverter.toData(any(Teacher.class))).thenReturn(teacherData);
        when(teacherConverter.fromData(any(TeacherData.class))).thenReturn(teacher);
        when(teacherRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(DbRecordAlreadyExistsException.class, () -> teacherServiceImpl.save(teacher));
    }

    @Test
    void addTeacherReturnsTeacherIfNotExists() {
        Teacher teacher = Teacher.builder().build();
        TeacherData teacherData = new TeacherData();

        when(teacherRepository.existsByName(anyString())).thenReturn(false);
        when(teacherConverter.toData(any(Teacher.class))).thenReturn(teacherData);
        when(teacherRepository.save(any(TeacherData.class))).thenReturn(teacherData);
        when(teacherConverter.fromData(any(TeacherData.class))).thenReturn(teacher);

        assertEquals(teacher, teacherServiceImpl.save(teacher));
    }

    @Test
    void addTeacherThrowsExceptionOnDataIntegrityViolation() {
        Teacher teacher = Teacher.builder().build();
        TeacherData teacherData = new TeacherData();

        when(teacherRepository.existsByName(anyString())).thenReturn(false);
        when(teacherConverter.toData(any(Teacher.class))).thenReturn(teacherData);
        when(teacherRepository.save(any(TeacherData.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> teacherServiceImpl.save(teacher));
    }
}