package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.services.SubjectServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectServiceImpl subjectServiceImpl;

    @GetMapping("/all")
    public List<Subject> getAllSubjects() {
        return subjectServiceImpl.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable Long id) {
        return subjectServiceImpl.getSubject(id);
    }

    @PostMapping("/")
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectServiceImpl.addSubject(subject);
    }
}
