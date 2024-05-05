package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.converters.Converter;
import ua.nure.progtheory.lab.data.GroupData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.repositories.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final Converter<Group, GroupData> groupConverter;

    private final GroupRepository groupRepository;

    public List<Group> getAll() {
        List<GroupData> groupsData = groupRepository.findAll();

        if (groupsData.isEmpty()) {
            throw new ResourceNotFoundException("No groups found");
        }

        return groupsData.stream()
                .map(groupConverter::fromData)
                .toList();
    }

    public Group get(Long id) {
        GroupData groupData = groupRepository.findById(id).orElse(null);

        if (groupData == null) {
            throw new ResourceNotFoundException("Group not found");
        }

        return groupConverter.fromData(groupData);
    }

    public Group save(Group group) {
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
