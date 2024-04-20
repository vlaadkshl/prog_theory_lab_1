package ua.nure.progtheory.lab;

import org.springframework.web.bind.annotation.*;
import ua.nure.progtheory.lab.data.Group;
import ua.nure.progtheory.lab.data.Lesson;
import ua.nure.progtheory.lab.data.Subject;
import ua.nure.progtheory.lab.data.Teacher;
import ua.nure.progtheory.lab.repositories.GroupRepository;
import ua.nure.progtheory.lab.repositories.LessonRepository;
import ua.nure.progtheory.lab.repositories.SubjectRepository;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final LessonRepository lessonRepository;

    public AppController(GroupRepository groupRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository, LessonRepository lessonRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping("/group/all")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/teacher/all")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/subject/all")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @PostMapping("/group")
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

    @PostMapping("/teacher")
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PostMapping("/subject")
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @GetMapping("/lesson/byGroup/{groupId}")
    public List<Lesson> getLessonsByGroup(@PathVariable Long groupId) {
        return lessonRepository.findByGroupId(groupId);
    }

    @GetMapping("/lesson/byTeacher/{teacherId}")
    public List<Lesson> getLessonsByTeacher(@PathVariable Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId);
    }

    @GetMapping("/lesson/bySubject/{subjectId}")
    public List<Lesson> getLessonsBySubject(@PathVariable Long subjectId) {
        return lessonRepository.findBySubjectId(subjectId);
    }

    @PostMapping("/lesson")
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @DeleteMapping("/lesson/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
