package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Usuario;
import java.util.List;

public interface IUsuarioService {
    List<Usuario> listar();
    void registrar(Usuario u);
    Usuario buscarPorId(Long id);
    void modificar(Usuario u);
    void eliminar(Long id);
    Usuario buscarPorUsername(String username);
}
