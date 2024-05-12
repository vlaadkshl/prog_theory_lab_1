package ua.nure.progtheory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.entity.UpdateDataEntity;

public interface UpdateDataEntityRepository extends JpaRepository<UpdateDataEntity, Long> {
}