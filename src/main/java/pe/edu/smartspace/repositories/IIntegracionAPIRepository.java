package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.IntegracionAPI;

public interface IIntegracionAPIRepository extends JpaRepository<IntegracionAPI, Long> {
}
