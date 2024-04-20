package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.data.GroupData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        List<GroupData> groupsData = groupRepository.findAll();

        return groupsData.stream()
                .map(groupData -> Group.builder()
                        .id(groupData.getId())
                        .name(groupData.getName())
                        .build())
                .toList();
    }

    public Group getGroup(Long id) {
        GroupData groupData = groupRepository.findById(id).orElse(null);

        assert groupData != null;
        return Group.builder()
                .id(groupData.getId())
                .name(groupData.getName())
                .build();
    }

    public Group addGroup(Group group) {
        GroupData groupData = GroupData.builder()
                .name(group.getName())
                .build();

        if (groupRepository.existsByName(group.getName())) {
            throw new DbRecordAlreadyExistsException("Group with name " + group.getName() + " already exists");
        }

        try {
            groupData = groupRepository.save(groupData);
            return Group.builder()
                    .id(groupData.getId())
                    .name(groupData.getName())
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add group due to data integrity violation", ex);
        }
    }
}
