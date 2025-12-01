package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.smartspace.entities.Comentario;

import java.util.List;

public interface IComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c WHERE c.diseno.idDiseno = :idDiseno")
    List<Comentario> listarPorDiseno(@Param("idDiseno") Long idDiseno);
}
