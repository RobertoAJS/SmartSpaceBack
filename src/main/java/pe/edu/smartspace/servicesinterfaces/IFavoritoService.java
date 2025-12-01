package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Favorito;
import java.util.List;

public interface IFavoritoService {

    List<Favorito> listar();
    List<Favorito> listarPorUsuario(Long idUsuario);

    void registrar(Favorito f);
    Favorito buscarPorId(Long id);
    void modificar(Favorito f);
    void eliminar(Long id);

    boolean perteneceAlUsuario(Long favoritoId, Long userId);
}


