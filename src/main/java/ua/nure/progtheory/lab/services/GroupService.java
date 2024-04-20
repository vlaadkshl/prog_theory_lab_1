package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.converters.GroupConverter;
import ua.nure.progtheory.lab.data.GroupData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupConverter groupConverter;

    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        List<GroupData> groupsData = groupRepository.findAll();

        return groupsData.stream()
                .map(groupConverter::fromData)
                .toList();
    }

    public Group getGroup(Long id) {
        GroupData groupData = groupRepository.findById(id).orElse(null);

        if (groupData == null) {
            return null;
        }

        return groupConverter.fromData(groupData);
    }

    public Group addGroup(Group group) {
        GroupData groupData = groupConverter.toData(group);

        if (groupRepository.existsByName(group.getName())) {
            throw new DbRecordAlreadyExistsException("Group with name " + group.getName() + " already exists");
        }

        try {
            groupData = groupRepository.save(groupData);
            return groupConverter.fromData(groupData);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add group due to data integrity violation", ex);
        }
    }
}
