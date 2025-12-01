package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.Mueble;

public interface IMuebleRepository extends JpaRepository<Mueble, Long> {
}
