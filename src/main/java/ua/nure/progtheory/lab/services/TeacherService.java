package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.converters.TeacherConverter;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherConverter teacherConverter;

    public List<Teacher> getAllTeachers() {
        var teachersData = teacherRepository.findAll();

        return teachersData.stream()
                .map(teacherConverter::fromData)
                .toList();
    }

    public Teacher getTeacher(Long id) {
        var teacherData = teacherRepository.findById(id).orElse(null);

        if (teacherData == null) {
            return null;
        }

        return teacherConverter.fromData(teacherData);
    }

    public Teacher addTeacher(Teacher teacher) {
        var teacherData = teacherConverter.toData(teacher);

        if (teacherRepository.existsByName(teacherData.getName())) {
            throw new DbRecordAlreadyExistsException("Teacher " + teacherData.getName() + " already exists");
        }

        try {
            teacherData = teacherRepository.save(teacherData);
            return teacherConverter.fromData(teacherData);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add teacher due to data integrity violation", ex);
        }
    }
}
