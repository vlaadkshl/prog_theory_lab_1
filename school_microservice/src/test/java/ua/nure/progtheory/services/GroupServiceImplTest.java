package ua.nure.progtheory.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.nure.progtheory.business.Group;
import ua.nure.progtheory.converters.GroupConverter;
import ua.nure.progtheory.data.GroupData;
import ua.nure.progtheory.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.repositories.GroupRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupConverter groupConverter;

    @InjectMocks
    private GroupServiceImpl groupServiceImpl;

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

        assertEquals(Arrays.asList(group1, group2), groupServiceImpl.getAll());
    }

    @Test
    void getGroupReturnsGroupIfExists() {
        GroupData groupData = new GroupData();
        Group group = Group.builder().build();

        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(groupData));
        when(groupConverter.fromData(groupData)).thenReturn(group);

        assertEquals(group, groupServiceImpl.get(1L));
    }

    @Test
    void getGroupReturnsNullIfNotExists() {
        when(groupRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupServiceImpl.get(1L));
    }

    @Test
    void addGroupThrowsExceptionIfGroupExists() {
        Group group = Group.builder().build();
        group.setName("Existing group");

        when(groupRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(DbRecordAlreadyExistsException.class, () -> groupServiceImpl.save(group));
    }

    @Test
    void addGroupReturnsGroupIfNotExists() {
        Group group = Group.builder().build();
        GroupData groupData = new GroupData();

        when(groupRepository.existsByName(anyString())).thenReturn(false);
        when(groupConverter.toData(any(Group.class))).thenReturn(groupData);
        when(groupRepository.save(any(GroupData.class))).thenReturn(groupData);
        when(groupConverter.fromData(any(GroupData.class))).thenReturn(group);

        assertEquals(group, groupServiceImpl.save(group));
    }

    @Test
    void addGroupThrowsExceptionOnDataIntegrityViolation() {
        Group group = Group.builder().build();
        GroupData groupData = new GroupData();

        when(groupRepository.existsByName(anyString())).thenReturn(false);
        when(groupConverter.toData(any(Group.class))).thenReturn(groupData);
        when(groupRepository.save(any(GroupData.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(RuntimeException.class, () -> groupServiceImpl.save(group));
    }
}