package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.Comentario;

public interface IComentarioRepository extends JpaRepository<Comentario, Long> {
}
