package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.data.Teacher;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
}
