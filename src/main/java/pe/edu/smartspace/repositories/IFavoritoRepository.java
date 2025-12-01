package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.smartspace.entities.Favorito;

import java.util.List;

public interface IFavoritoRepository extends JpaRepository<Favorito, Long> {

    // Consulta manual (opcional pero válida)
    @Query("SELECT f FROM Favorito f WHERE f.usuario.idUsuario = :idUsuario")
    List<Favorito> listarPorUsuario(@Param("idUsuario") Long idUsuario);

    // Consulta automática (para el service)
    List<Favorito> findByUsuario_IdUsuario(Long idUsuario);

    // Verificar propiedad del favorito
    @Query("SELECT COUNT(f) > 0 FROM Favorito f WHERE f.idFavorito = :favoritoId AND f.usuario.idUsuario = :userId")
    boolean perteneceAlUsuario(@Param("favoritoId") Long favoritoId, @Param("userId") Long userId);
}


