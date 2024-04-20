package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}