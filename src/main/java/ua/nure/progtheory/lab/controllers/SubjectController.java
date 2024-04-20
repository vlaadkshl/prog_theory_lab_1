package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.data.Subject;
import ua.nure.progtheory.lab.repositories.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;

    @GetMapping("/all")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
}
