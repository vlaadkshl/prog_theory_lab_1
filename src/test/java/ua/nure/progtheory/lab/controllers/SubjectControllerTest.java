package ua.nure.progtheory.lab.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.exceptions.GlobalExceptionHandler;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.services.SubjectService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SubjectControllerTest {

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllSubjectsReturnsEmptyListWhenNoSubjectsExist() throws Exception {
        when(subjectService.getAllSubjects()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/subject/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(subjectService, times(1)).getAllSubjects();
    }

    @Test
    void getSubjectReturnsSubjectWhenExists() throws Exception {
        Subject subject = Subject.builder().id(1L).build();
        when(subjectService.getSubject(1L)).thenReturn(subject);

        mockMvc.perform(get("/api/subject/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(subjectService, times(1)).getSubject(1L);
    }

    @Test
    void getSubjectReturnsNotFoundWhenNotExists() throws Exception {
        when(subjectService.getSubject(1L)).thenThrow(new ResourceNotFoundException("Subject not found"));

        mockMvc.perform(get("/api/subject/1"))
                .andExpect(status().isNotFound());

        verify(subjectService, times(1)).getSubject(1L);
    }

    @Test
    void addSubjectReturnsCreatedSubject() throws Exception {
        Subject subject = Subject.builder().id(1L).build();
        when(subjectService.addSubject(any(Subject.class))).thenReturn(subject);

        mockMvc.perform(post("/api/subject/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(subjectService, times(1)).addSubject(any(Subject.class));
    }
}