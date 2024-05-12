package ua.nure.progtheory.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.nure.progtheory.business.Lesson;
import ua.nure.progtheory.exceptions.GlobalExceptionHandler;
import ua.nure.progtheory.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.services.LessonServiceImpl;
import ua.nure.progtheory.services.audition.AuditorService;

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
    private AuditorService auditorService;

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
        when(lessonServiceImpl.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/lesson/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(lessonServiceImpl, times(1)).getAll();
    }

    @Test
    void getLessonReturnsLessonWhenExists() throws Exception {
        Lesson lesson = Lesson.builder().id(1L).build();
        when(lessonServiceImpl.get(1L)).thenReturn(lesson);

        mockMvc.perform(get("/api/lesson/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(lessonServiceImpl, times(1)).get(1L);
    }

    @Test
    void getLessonReturnsNotFoundWhenNotExists() throws Exception {
        when(lessonServiceImpl.get(1L)).thenThrow(new ResourceNotFoundException("Lesson not found"));

        mockMvc.perform(get("/api/lesson/1"))
                .andExpect(status().isNotFound());

        verify(lessonServiceImpl, times(1)).get(1L);
    }

    @Test
    void addLessonReturnsCreatedLesson() throws Exception {
        Lesson lesson = Lesson.builder().id(1L).build();
        when(lessonServiceImpl.save(any(Lesson.class))).thenReturn(lesson);

        mockMvc.perform(post("/api/lesson/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(lessonServiceImpl, times(1)).save(any(Lesson.class));
    }

    @Test
    void deleteLessonExecutesDeleteWhenLessonExists() throws Exception {
        doNothing().when(lessonServiceImpl).delete(1L);

        mockMvc.perform(delete("/api/lesson/1"))
                .andExpect(status().isOk());

        verify(lessonServiceImpl, times(1)).delete(1L);
    }
}