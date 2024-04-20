package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.data.Lesson;
import ua.nure.progtheory.lab.repositories.LessonRepository;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonRepository lessonRepository;

    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @GetMapping("/{lessonId}")
    public Lesson getLesson(@PathVariable Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }

    @GetMapping("/byGroup/{groupId}")
    public List<Lesson> getLessonsByGroup(@PathVariable Long groupId) {
        return lessonRepository.findByGroupId(groupId);
    }

    @GetMapping("/byTeacher/{teacherId}")
    public List<Lesson> getLessonsByTeacher(@PathVariable Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId);
    }

    @GetMapping("/bySubject/{subjectId}")
    public List<Lesson> getLessonsBySubject(@PathVariable Long subjectId) {
        return lessonRepository.findBySubjectId(subjectId);
    }

    @PostMapping("/")
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
