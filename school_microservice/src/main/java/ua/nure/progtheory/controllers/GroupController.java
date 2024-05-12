package ua.nure.progtheory.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.business.Group;
import ua.nure.progtheory.services.ResourceReceiver;
import ua.nure.progtheory.services.ResourceSaver;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final ResourceReceiver<Group> groupGetter;

    private final ResourceSaver<Group> groupSaver;

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupGetter.getAll();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return groupGetter.get(id);
    }

    @PostMapping("/")
    public Group addGroup(@RequestBody Group group) {
        return groupSaver.save(group);
    }
}
