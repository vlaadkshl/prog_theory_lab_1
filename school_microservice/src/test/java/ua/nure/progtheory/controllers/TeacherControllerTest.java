package ua.nure.progtheory.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.nure.progtheory.business.Teacher;
import ua.nure.progtheory.exceptions.GlobalExceptionHandler;
import ua.nure.progtheory.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.services.TeacherServiceImpl;
import ua.nure.progtheory.services.audition.AuditorService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private AuditorService auditorService;

    @Mock
    private TeacherServiceImpl teacherServiceImpl;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllTeachersReturnsEmptyListWhenNoTeachersExist() throws Exception {
        when(teacherServiceImpl.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/teacher/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(teacherServiceImpl, times(1)).getAll();
    }

    @Test
    void getTeacherReturnsTeacherWhenExists() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).build();
        when(teacherServiceImpl.get(1L)).thenReturn(teacher);

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(teacherServiceImpl, times(1)).get(1L);
    }

    @Test
    void getTeacherReturnsNotFoundWhenNotExists() throws Exception {
        when(teacherServiceImpl.get(1L)).thenThrow(new ResourceNotFoundException("Teacher not found"));

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isNotFound());

        verify(teacherServiceImpl, times(1)).get(1L);
    }

    @Test
    void addTeacherReturnsCreatedTeacher() throws Exception {
        Teacher teacher = Teacher.builder().id(1L).build();
        when(teacherServiceImpl.save(any(Teacher.class))).thenReturn(teacher);

        mockMvc.perform(post("/api/teacher/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(teacherServiceImpl, times(1)).save(any(Teacher.class));
    }
}