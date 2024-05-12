package ua.nure.progtheory.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.business.Teacher;
import ua.nure.progtheory.services.ResourceReceiver;
import ua.nure.progtheory.services.ResourceSaver;
import ua.nure.progtheory.services.audition.AuditorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {

    private final AuditorService auditorService;

    private final ResourceReceiver<Teacher> teacherReceiver;

    private final ResourceSaver<Teacher> teacherSaver;

    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        auditorService.auditReading("all", "teacher");
        return teacherReceiver.getAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Long id) {
        auditorService.auditReading("byId: " + id, "teacher");
        return teacherReceiver.get(id);
    }

    @PostMapping("/")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        auditorService.auditCreation(teacher, "teacher");
        return teacherSaver.save(teacher);
    }
}
