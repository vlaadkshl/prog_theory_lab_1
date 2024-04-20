package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Boolean existsByName(String name);
}