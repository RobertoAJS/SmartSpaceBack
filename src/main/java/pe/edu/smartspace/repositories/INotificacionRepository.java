package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.Notificacion;

import java.util.List;

public interface INotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuario_IdUsuario(Long usuarioId);
}
