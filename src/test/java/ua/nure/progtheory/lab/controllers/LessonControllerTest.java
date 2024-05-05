package ua.nure.progtheory.lab.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.exceptions.GlobalExceptionHandler;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.services.LessonServiceImpl;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LessonControllerTest {

    @InjectMocks
    private LessonController lessonController;

    @Mock
    private LessonServiceImpl lessonServiceImpl;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllLessonsReturnsEmptyListWhenNoLessonsExist() throws Exception {
        when(lessonServiceImpl.getAllLessons()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/lesson/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(lessonServiceImpl, times(1)).getAllLessons();
    }

    @Test
    void getLessonReturnsLessonWhenExists() throws Exception {
        Lesson lesson = Lesson.builder().id(1L).build();
        when(lessonServiceImpl.getLesson(1L)).thenReturn(lesson);

        mockMvc.perform(get("/api/lesson/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(lessonServiceImpl, times(1)).getLesson(1L);
    }

    @Test
    void getLessonReturnsNotFoundWhenNotExists() throws Exception {
        when(lessonServiceImpl.getLesson(1L)).thenThrow(new ResourceNotFoundException("Lesson not found"));

        mockMvc.perform(get("/api/lesson/1"))
                .andExpect(status().isNotFound());

        verify(lessonServiceImpl, times(1)).getLesson(1L);
    }

    @Test
    void addLessonReturnsCreatedLesson() throws Exception {
        Lesson lesson = Lesson.builder().id(1L).build();
        when(lessonServiceImpl.addLesson(any(Lesson.class))).thenReturn(lesson);

        mockMvc.perform(post("/api/lesson/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(lessonServiceImpl, times(1)).addLesson(any(Lesson.class));
    }

    @Test
    void deleteLessonExecutesDeleteWhenLessonExists() throws Exception {
        doNothing().when(lessonServiceImpl).deleteLesson(1L);

        mockMvc.perform(delete("/api/lesson/1"))
                .andExpect(status().isOk());

        verify(lessonServiceImpl, times(1)).deleteLesson(1L);
    }
}