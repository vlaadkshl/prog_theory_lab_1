package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.data.Group;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.GroupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group addGroup(Group group) {
        if (groupRepository.existsByName(group.getName())) {
            throw new DbRecordAlreadyExistsException("Group with name " + group.getName() + " already exists");
        }

        try {
            return groupRepository.save(group);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add group due to data integrity violation", ex);
        }
    }
}
