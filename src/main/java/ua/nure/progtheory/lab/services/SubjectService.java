package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.data.SubjectData;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.SubjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        var subjectData = subjectRepository.findAll();

        return subjectData.stream()
                .map(subject -> Subject.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .teachers(subject.getTeachers().stream()
                                .map(teacherData -> Teacher.builder()
                                        .id(teacherData.getId())
                                        .name(teacherData.getName())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    public Subject getSubject(Long id) {
        var subjectData = subjectRepository.findById(id).orElse(null);

        assert subjectData != null;
        return Subject.builder()
                .id(subjectData.getId())
                .name(subjectData.getName())
                .teachers(subjectData.getTeachers().stream()
                        .map(teacherData -> Teacher.builder()
                                .id(teacherData.getId())
                                .name(teacherData.getName())
                                .build())
                        .toList())
                .build();
    }

    public Subject addSubject(Subject subject) {
        var subjectData = SubjectData.builder()
                .name(subject.getName())
                .teachers(subject.getTeachers().stream()
                        .map(teacher -> TeacherData.builder()
                                .name(teacher.getName())
                                .build())
                        .toList())
                .build();

        if (subjectRepository.existsByNameAndTeachers(subjectData.getName(), subjectData.getTeachers())) {
            throw new DbRecordAlreadyExistsException(
                    "Subject with name " + subjectData.getName() + " and teachers "
                            + subjectData.getTeachers().stream().map(TeacherData::getName).toList()
                            + " already exists");
        }

        try {
            subjectData = subjectRepository.save(subjectData);

            return Subject.builder()
                    .id(subjectData.getId())
                    .name(subjectData.getName())
                    .teachers(subjectData.getTeachers().stream()
                            .map(teacherData -> Teacher.builder()
                                    .id(teacherData.getId())
                                    .name(teacherData.getName())
                                    .build())
                            .toList())
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add subject due to data integrity violation", ex);
        }
    }
}
