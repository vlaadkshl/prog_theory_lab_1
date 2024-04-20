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
        if (groupRepository.existsById(group.getId())) {
            throw new DbRecordAlreadyExistsException("Group with ID " + group.getId() + " already exists");
        }

        try {
            return groupRepository.save(group);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add group due to data integrity violation", ex);
        }
    }
}
