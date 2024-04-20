package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.services.LessonService;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{lessonId}")
    public Lesson getLesson(@PathVariable Long lessonId) {
        return lessonService.getLesson(lessonId);
    }

    @GetMapping("/byGroup/{groupId}")
    public List<Lesson> getLessonsByGroup(@PathVariable Long groupId) {
        return lessonService.getLessonsByGroup(groupId);
    }

    @GetMapping("/byTeacher/{teacherId}")
    public List<Lesson> getLessonsByTeacher(@PathVariable Long teacherId) {
        return lessonService.getLessonsByTeacher(teacherId);
    }

    @GetMapping("/bySubject/{subjectId}")
    public List<Lesson> getLessonsBySubject(@PathVariable Long subjectId) {
        return lessonService.getLessonsBySubject(subjectId);
    }

    @PostMapping("/")
    public Lesson addLesson(@RequestBody Lesson lesson) {
        return lessonService.addLesson(lesson);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
    }
}
