package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.services.ResourceReceiver;
import ua.nure.progtheory.lab.services.ResourceSaver;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {

    private final ResourceReceiver<Teacher> teacherReceiver;

    private final ResourceSaver<Teacher> teacherSaver;

    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherReceiver.getAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Long id) {
        return teacherReceiver.get(id);
    }

    @PostMapping("/")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherSaver.save(teacher);
    }
}
