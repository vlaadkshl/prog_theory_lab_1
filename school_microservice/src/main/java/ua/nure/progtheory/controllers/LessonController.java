package ua.nure.progtheory.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.business.Lesson;
import ua.nure.progtheory.services.LessonService;
import ua.nure.progtheory.services.ResourceReceiver;
import ua.nure.progtheory.services.ResourceRemover;
import ua.nure.progtheory.services.ResourceSaver;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final ResourceReceiver<Lesson> lessonsReceiver;

    private final ResourceSaver<Lesson> lessonsSaver;

    private final ResourceRemover<Lesson> lessonsRemover;

    private final LessonService lessonServiceImpl;

    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonsReceiver.getAll();
    }

    @GetMapping("/{lessonId}")
    public Lesson getLesson(@PathVariable Long lessonId) {
        return lessonsReceiver.get(lessonId);
    }

    @GetMapping("/byGroup/{groupId}")
    public List<Lesson> getLessonsByGroup(@PathVariable Long groupId) {
        return lessonServiceImpl.getByGroup(groupId);
    }

    @GetMapping("/byTeacher/{teacherId}")
    public List<Lesson> getLessonsByTeacher(@PathVariable Long teacherId) {
        return lessonServiceImpl.getByTeacher(teacherId);
    }

    @GetMapping("/bySubject/{subjectId}")
    public List<Lesson> getLessonsBySubject(@PathVariable Long subjectId) {
        return lessonServiceImpl.getLessonsBySubject(subjectId);
    }

    @PostMapping("/")
    public Lesson addLesson(@RequestBody Lesson lesson) {
        return lessonsSaver.save(lesson);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonsRemover.delete(lessonId);
    }
}
