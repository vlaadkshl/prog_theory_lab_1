package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.data.Teacher;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher addTeacher(Teacher teacher) {
        if (teacherRepository.existsById(teacher.getId())) {
            throw new DbRecordAlreadyExistsException("Teacher with ID " + teacher.getId() + " already exists");
        }

        try {
            return teacherRepository.save(teacher);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add teacher due to data integrity violation", ex);
        }
    }
}
