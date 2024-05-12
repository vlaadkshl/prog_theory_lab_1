package ua.nure.progtheory.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.business.Subject;
import ua.nure.progtheory.services.ResourceReceiver;
import ua.nure.progtheory.services.ResourceSaver;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final ResourceReceiver<Subject> subjectReceiver;

    private final ResourceSaver<Subject> subjectSaver;

    @GetMapping("/all")
    public List<Subject> getAllSubjects() {
        return subjectReceiver.getAll();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable Long id) {
        return subjectReceiver.get(id);
    }

    @PostMapping("/")
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectSaver.save(subject);
    }
}
