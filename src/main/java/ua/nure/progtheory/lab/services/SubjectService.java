package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.data.Subject;
import ua.nure.progtheory.lab.data.Teacher;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.SubjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubject(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public Subject addSubject(Subject subject) {
        if (subjectRepository.existsByNameAndTeachers(subject.getName(), subject.getTeachers())) {
            throw new DbRecordAlreadyExistsException(
                    "Subject with name " + subject.getName() + " and teachers "
                            + subject.getTeachers().stream().map(Teacher::getName).toList()
                            + " already exists");
        }

        try {
            return subjectRepository.save(subject);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add subject due to data integrity violation", ex);
        }
    }
}
