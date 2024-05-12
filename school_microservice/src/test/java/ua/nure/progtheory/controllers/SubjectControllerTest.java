package ua.nure.progtheory.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.nure.progtheory.business.Subject;
import ua.nure.progtheory.exceptions.GlobalExceptionHandler;
import ua.nure.progtheory.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.services.SubjectServiceImpl;

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
    private SubjectServiceImpl subjectServiceImpl;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllSubjectsReturnsEmptyListWhenNoSubjectsExist() throws Exception {
        when(subjectServiceImpl.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/subject/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(subjectServiceImpl, times(1)).getAll();
    }

    @Test
    void getSubjectReturnsSubjectWhenExists() throws Exception {
        Subject subject = Subject.builder().id(1L).build();
        when(subjectServiceImpl.get(1L)).thenReturn(subject);

        mockMvc.perform(get("/api/subject/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(subjectServiceImpl, times(1)).get(1L);
    }

    @Test
    void getSubjectReturnsNotFoundWhenNotExists() throws Exception {
        when(subjectServiceImpl.get(1L)).thenThrow(new ResourceNotFoundException("Subject not found"));

        mockMvc.perform(get("/api/subject/1"))
                .andExpect(status().isNotFound());

        verify(subjectServiceImpl, times(1)).get(1L);
    }

    @Test
    void addSubjectReturnsCreatedSubject() throws Exception {
        Subject subject = Subject.builder().id(1L).build();
        when(subjectServiceImpl.save(any(Subject.class))).thenReturn(subject);

        mockMvc.perform(post("/api/subject/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(subjectServiceImpl, times(1)).save(any(Subject.class));
    }
}