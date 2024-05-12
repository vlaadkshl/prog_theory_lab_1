package ua.nure.progtheory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.data.GroupData;

public interface GroupRepository extends JpaRepository<GroupData, Long> {
    Boolean existsByName(String name);
}