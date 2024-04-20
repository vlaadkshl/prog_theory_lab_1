package ua.nure.progtheory.lab.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.converters.GroupConverter;
import ua.nure.progtheory.lab.data.GroupData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.GroupRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupConverter groupConverter;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGroupsReturnsAllGroups() {
        GroupData groupData1 = new GroupData();
        GroupData groupData2 = new GroupData();
        Group group1 = Group.builder().build();
        Group group2 = Group.builder().build();

        when(groupRepository.findAll()).thenReturn(Arrays.asList(groupData1, groupData2));
        when(groupConverter.fromData(groupData1)).thenReturn(group1);
        when(groupConverter.fromData(groupData2)).thenReturn(group2);

        assertEquals(Arrays.asList(group1, group2), groupService.getAllGroups());
    }

    @Test
    void getGroupReturnsGroupIfExists() {
        GroupData groupData = new GroupData();
        Group group = Group.builder().build();

        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(groupData));
        when(groupConverter.fromData(groupData)).thenReturn(group);

        assertEquals(group, groupService.getGroup(1L));
    }

    @Test
    void getGroupReturnsNullIfNotExists() {
        when(groupRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(groupService.getGroup(1L));
    }

    @Test
    void addGroupThrowsExceptionIfGroupExists() {
        Group group = Group.builder().build();
        group.setName("Existing group");

        when(groupRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(DbRecordAlreadyExistsException.class, () -> groupService.addGroup(group));
    }

    @Test
    void addGroupReturnsGroupIfNotExists() {
        Group group = Group.builder().build();
        GroupData groupData = new GroupData();

        when(groupRepository.existsByName(anyString())).thenReturn(false);
        when(groupConverter.toData(any(Group.class))).thenReturn(groupData);
        when(groupRepository.save(any(GroupData.class))).thenReturn(groupData);
        when(groupConverter.fromData(any(GroupData.class))).thenReturn(group);

        assertEquals(group, groupService.addGroup(group));
    }

    @Test
    void addGroupThrowsExceptionOnDataIntegrityViolation() {
        Group group = Group.builder().build();
        GroupData groupData = new GroupData();

        when(groupRepository.existsByName(anyString())).thenReturn(false);
        when(groupConverter.toData(any(Group.class))).thenReturn(groupData);
        when(groupRepository.save(any(GroupData.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> groupService.addGroup(group));
    }
}