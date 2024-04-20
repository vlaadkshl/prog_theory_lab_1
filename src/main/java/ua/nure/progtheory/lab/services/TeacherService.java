package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        var teachersData = teacherRepository.findAll();

        return teachersData.stream()
                .map(teacher -> Teacher.builder()
                        .id(teacher.getId())
                        .name(teacher.getName())
                        .build())
                .toList();
    }

    public Teacher getTeacher(Long id) {
        var teacherData = teacherRepository.findById(id).orElse(null);

        return teacherData == null ? null : Teacher.builder()
                .id(teacherData.getId())
                .name(teacherData.getName())
                .build();
    }

    public Teacher addTeacher(Teacher teacher) {
        var teacherData = TeacherData.builder()
                .name(teacher.getName())
                .build();

        if (teacherRepository.existsByName(teacherData.getName())) {
            throw new DbRecordAlreadyExistsException("Teacher " + teacherData.getName() + " already exists");
        }

        try {
            teacherData = teacherRepository.save(teacherData);

            return Teacher.builder()
                    .id(teacherData.getId())
                    .name(teacherData.getName())
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add teacher due to data integrity violation", ex);
        }
    }
}
