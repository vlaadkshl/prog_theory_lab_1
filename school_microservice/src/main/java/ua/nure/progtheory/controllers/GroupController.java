package ua.nure.progtheory.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.business.Group;
import ua.nure.progtheory.services.ResourceReceiver;
import ua.nure.progtheory.services.ResourceSaver;
import ua.nure.progtheory.services.audition.AuditorService;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final AuditorService auditorService;

    private final ResourceReceiver<Group> groupGetter;

    private final ResourceSaver<Group> groupSaver;

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        auditorService.auditReading("all", "group");
        return groupGetter.getAll();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        auditorService.auditReading("byId: " + id, "group");
        return groupGetter.get(id);
    }

    @PostMapping("/")
    public Group addGroup(@RequestBody Group group) {
        auditorService.auditCreation(group.toString(), "group");
        return groupSaver.save(group);
    }
}
