package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.business.Group;
import ua.nure.progtheory.lab.services.GroupServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupServiceImpl groupServiceImpl;

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupServiceImpl.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return groupServiceImpl.getGroup(id);
    }

    @PostMapping("/")
    public Group addGroup(@RequestBody Group group) {
        return groupServiceImpl.addGroup(group);
    }
}
