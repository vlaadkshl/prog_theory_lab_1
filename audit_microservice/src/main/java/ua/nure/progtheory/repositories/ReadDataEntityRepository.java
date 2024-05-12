package ua.nure.progtheory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.entity.ReadDataEntity;

public interface ReadDataEntityRepository extends JpaRepository<ReadDataEntity, Long> {
}