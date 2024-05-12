package ua.nure.progtheory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.nure.progtheory.business.Subject;
import ua.nure.progtheory.business.Teacher;
import ua.nure.progtheory.converters.SubjectConverter;
import ua.nure.progtheory.data.SubjectData;
import ua.nure.progtheory.data.TeacherData;
import ua.nure.progtheory.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.repositories.SubjectRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SubjectServiceImplTest {

    @Mock
    SubjectRepository subjectRepository;

    @Mock
    SubjectConverter subjectConverter;

    @InjectMocks
    SubjectServiceImpl subjectServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSubjectsReturnsAllSubjects() {
        SubjectData subjectData1 = new SubjectData();
        SubjectData subjectData2 = new SubjectData();
        Subject subject1 = Subject.builder().build();
        Subject subject2 = Subject.builder().build();

        when(subjectRepository.findAll()).thenReturn(Arrays.asList(subjectData1, subjectData2));
        when(subjectConverter.fromData(subjectData1)).thenReturn(subject1);
        when(subjectConverter.fromData(subjectData2)).thenReturn(subject2);

        assertEquals(Arrays.asList(subject1, subject2), subjectServiceImpl.getAll());
    }

    @Test
    void getSubjectReturnsSubjectIfExists() {
        SubjectData subjectData = new SubjectData();
        Subject subject = Subject.builder().build();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subjectData));
        when(subjectConverter.fromData(subjectData)).thenReturn(subject);

        assertEquals(subject, subjectServiceImpl.get(1L));
    }

    @Test
    void getSubjectReturnsNullIfNotExists() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectServiceImpl.get(1L));
    }

    @Test
    void addSubjectThrowsExceptionIfSubjectExists() {
        Subject subject = Subject.builder()
                .id(1L)
                .name("name")
                .teachers(List.of(Teacher.builder().build()))
                .build();
        SubjectData subjectData = SubjectData.builder()
                .id(1L)
                .name("name")
                .teachers(List.of(TeacherData.builder().build()))
                .build();

        when(subjectConverter.toData(any(Subject.class))).thenReturn(subjectData);
        when(subjectConverter.fromData(any(SubjectData.class))).thenReturn(subject);
        when(subjectRepository.existsByNameAndTeachers(anyString(), anyList())).thenReturn(true);

        assertThrows(DbRecordAlreadyExistsException.class, () -> subjectServiceImpl.save(subject));
    }

    @Test
    void addSubjectReturnsSubjectIfNotExists() {
        Subject subject = Subject.builder().build();
        SubjectData subjectData = new SubjectData();

        when(subjectRepository.existsByNameAndTeachers(anyString(), anyList())).thenReturn(false);
        when(subjectConverter.toData(any(Subject.class))).thenReturn(subjectData);
        when(subjectRepository.save(any(SubjectData.class))).thenReturn(subjectData);
        when(subjectConverter.fromData(any(SubjectData.class))).thenReturn(subject);

        assertEquals(subject, subjectServiceImpl.save(subject));
    }

    @Test
    void addSubjectThrowsExceptionOnDataIntegrityViolation() {
        Subject subject = Subject.builder().build();
        SubjectData subjectData = new SubjectData();

        when(subjectRepository.existsByNameAndTeachers(anyString(), anyList())).thenReturn(false);
        when(subjectConverter.toData(any(Subject.class))).thenReturn(subjectData);
        when(subjectRepository.save(any(SubjectData.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> subjectServiceImpl.save(subject));
    }
}