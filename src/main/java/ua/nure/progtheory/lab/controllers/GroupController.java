package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.data.Group;
import ua.nure.progtheory.lab.repositories.GroupRepository;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupRepository groupRepository;

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }
}
