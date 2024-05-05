package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.services.TeacherServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherServiceImpl teacherServiceImpl;

    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherServiceImpl.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Long id) {
        return teacherServiceImpl.getTeacher(id);
    }

    @PostMapping("/")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherServiceImpl.addTeacher(teacher);
    }
}
