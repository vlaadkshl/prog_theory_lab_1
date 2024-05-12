package ua.nure.progtheory.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.nure.progtheory.business.Group;
import ua.nure.progtheory.exceptions.GlobalExceptionHandler;
import ua.nure.progtheory.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.services.GroupServiceImpl;
import ua.nure.progtheory.services.audition.AuditorService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GroupControllerTest {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private AuditorService auditorService;

    @Mock
    private GroupServiceImpl groupServiceImpl;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllGroupsReturnsEmptyListWhenNoGroupsExist() throws Exception {
        when(groupServiceImpl.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/group/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(groupServiceImpl, times(1)).getAll();
    }

    @Test
    void getGroupReturnsGroupWhenExists() throws Exception {
        Group group = Group.builder().id(1L).build();

        when(groupServiceImpl.get(1L)).thenReturn(group);

        mockMvc.perform(get("/api/group/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(groupServiceImpl, times(1)).get(1L);
    }

    @Test
    void getGroupReturnsNotFoundWhenNotExists() throws Exception {
        when(groupServiceImpl.get(1L)).thenThrow(new ResourceNotFoundException("Group not found"));

        mockMvc.perform(get("/api/group/1"))
                .andExpect(status().isNotFound());

        verify(groupServiceImpl, times(1)).get(1L);
    }

    @Test
    void addGroupReturnsCreatedGroup() throws Exception {
        Group group = Group.builder().id(1L).build();
        when(groupServiceImpl.save(any(Group.class))).thenReturn(group);

        mockMvc.perform(post("/api/group/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));

        verify(groupServiceImpl, times(1)).save(any(Group.class));
    }
}