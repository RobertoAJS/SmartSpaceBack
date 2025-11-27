package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.smartspace.entities.Mueble;
import java.util.List;

import java.util.List;

public interface IMuebleRepository extends JpaRepository<Mueble, Long> {
    // Buscar por coincidencia de categoría (ignorando mayúsculas/minúsculas)
    List<Mueble> findByCategoria(String categoria);
}
