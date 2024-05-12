package ua.nure.progtheory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.entity.CreateDataEntity;

public interface CreateDataEntityRepository extends JpaRepository<CreateDataEntity, Long> {
}