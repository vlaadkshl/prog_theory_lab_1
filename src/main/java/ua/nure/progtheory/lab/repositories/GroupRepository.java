package ua.nure.progtheory.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.lab.data.GroupData;

public interface GroupRepository extends JpaRepository<GroupData, Long> {
    Boolean existsByName(String name);
}