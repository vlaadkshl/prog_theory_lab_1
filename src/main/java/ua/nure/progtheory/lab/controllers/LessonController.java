package ua.nure.progtheory.lab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.business.Lesson;
import ua.nure.progtheory.lab.services.LessonServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonServiceImpl lessonServiceImpl;

    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonServiceImpl.getAllLessons();
    }

    @GetMapping("/{lessonId}")
    public Lesson getLesson(@PathVariable Long lessonId) {
        return lessonServiceImpl.getLesson(lessonId);
    }

    @GetMapping("/byGroup/{groupId}")
    public List<Lesson> getLessonsByGroup(@PathVariable Long groupId) {
        return lessonServiceImpl.getLessonsByGroup(groupId);
    }

    @GetMapping("/byTeacher/{teacherId}")
    public List<Lesson> getLessonsByTeacher(@PathVariable Long teacherId) {
        return lessonServiceImpl.getLessonsByTeacher(teacherId);
    }

    @GetMapping("/bySubject/{subjectId}")
    public List<Lesson> getLessonsBySubject(@PathVariable Long subjectId) {
        return lessonServiceImpl.getLessonsBySubject(subjectId);
    }

    @PostMapping("/")
    public Lesson addLesson(@RequestBody Lesson lesson) {
        return lessonServiceImpl.addLesson(lesson);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonServiceImpl.deleteLesson(lessonId);
    }
}
