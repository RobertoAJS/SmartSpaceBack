package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findOneByUsername(String username);
}

