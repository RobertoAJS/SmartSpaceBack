package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.Role;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    // Busca todos los roles de un usuario por su id
    List<Role> findByUsuario_IdUsuario(Long idUsuario);
}
