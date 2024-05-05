package ua.nure.progtheory.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.nure.progtheory.lab.business.Subject;
import ua.nure.progtheory.lab.converters.SubjectConverter;
import ua.nure.progtheory.lab.data.TeacherData;
import ua.nure.progtheory.lab.exceptions.DbRecordAlreadyExistsException;
import ua.nure.progtheory.lab.exceptions.ResourceNotFoundException;
import ua.nure.progtheory.lab.repositories.SubjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final SubjectConverter subjectConverter;

    @Override
    public List<Subject> getAll() {
        var subjectData = subjectRepository.findAll();

        if (subjectData.isEmpty()) {
            throw new ResourceNotFoundException("No subjects found");
        }

        return subjectData.stream()
                .map(subjectConverter::fromData)
                .toList();
    }

    @Override
    public Subject get(Long id) {
        var subjectData = subjectRepository.findById(id).orElse(null);

        if (subjectData == null) {
            throw new ResourceNotFoundException("Subject is not found");
        }

        return subjectConverter.fromData(subjectData);
    }

    @Override
    public Subject save(Subject subject) {
        var subjectData = subjectConverter.toData(subject);

        if (subjectRepository.existsByNameAndTeachers(subjectData.getName(), subjectData.getTeachers())) {
            throw new DbRecordAlreadyExistsException(
                    "Subject with name " + subjectData.getName() + " and teachers "
                            + subjectData.getTeachers().stream().map(TeacherData::getName).toList()
                            + " already exists");
        }

        try {
            subjectData = subjectRepository.save(subjectData);
            return subjectConverter.fromData(subjectData);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to add subject due to data integrity violation", ex);
        }
    }
}
