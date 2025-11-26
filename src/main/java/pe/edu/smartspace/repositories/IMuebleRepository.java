package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.smartspace.entities.Mueble;
import java.util.List;

@Repository
public interface IMuebleRepository extends JpaRepository<Mueble, Long> {

    List<Mueble> findByCategoria(String categoria);
}