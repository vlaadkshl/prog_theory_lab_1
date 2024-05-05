package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Teacher;
import ua.nure.progtheory.lab.converters.Converter;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.repositories.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final Converter<Teacher, TeacherData> teacherConverter;

    @Override
    public List<Teacher> getAll() {
        var teachersData = teacherRepository.findAll();

        if (teachersData.isEmpty()) {
            throw new ResourceNotFoundException("No teachers found");
        }

        return teachersData.stream()
                .map(teacherConverter::fromData)
                .toList();
    }

    @Override
    public Teacher get(Long id) {
        var teacherData = teacherRepository.findById(id).orElse(null);

        if (teacherData == null) {
            throw new ResourceNotFoundException("Teacher is not found");
        }

        return teacherConverter.fromData(teacherData);
    }

    @Override
    public Teacher save(Teacher teacher) {
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
