package ua.nure.progtheory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.progtheory.entity.DeleteDataEntity;

public interface DeleteDataEntityRepository extends JpaRepository<DeleteDataEntity, Long> {
}