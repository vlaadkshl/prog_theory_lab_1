package ua.nure.progtheory.lab.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.converters.SubjectConverter;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.SubjectRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    @Mock
    SubjectRepository subjectRepository;

    @Mock
    SubjectConverter subjectConverter;

    @InjectMocks
    SubjectService subjectService;

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

        assertEquals(Arrays.asList(subject1, subject2), subjectService.getAllSubjects());
    }

    @Test
    void getSubjectReturnsSubjectIfExists() {
        SubjectData subjectData = new SubjectData();
        Subject subject = Subject.builder().build();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subjectData));
        when(subjectConverter.fromData(subjectData)).thenReturn(subject);

        assertEquals(subject, subjectService.getSubject(1L));
    }

    @Test
    void getSubjectReturnsNullIfNotExists() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(subjectService.getSubject(1L));
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

        assertThrows(DbRecordAlreadyExistsException.class, () -> subjectService.addSubject(subject));
    }

    @Test
    void addSubjectReturnsSubjectIfNotExists() {
        Subject subject = Subject.builder().build();
        SubjectData subjectData = new SubjectData();

        when(subjectRepository.existsByNameAndTeachers(anyString(), anyList())).thenReturn(false);
        when(subjectConverter.toData(any(Subject.class))).thenReturn(subjectData);
        when(subjectRepository.save(any(SubjectData.class))).thenReturn(subjectData);
        when(subjectConverter.fromData(any(SubjectData.class))).thenReturn(subject);

        assertEquals(subject, subjectService.addSubject(subject));
    }

    @Test
    void addSubjectThrowsExceptionOnDataIntegrityViolation() {
        Subject subject = Subject.builder().build();
        SubjectData subjectData = new SubjectData();

        when(subjectRepository.existsByNameAndTeachers(anyString(), anyList())).thenReturn(false);
        when(subjectConverter.toData(any(Subject.class))).thenReturn(subjectData);
        when(subjectRepository.save(any(SubjectData.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> subjectService.addSubject(subject));
    }
}