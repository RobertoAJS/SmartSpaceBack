package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Favorito;
import java.util.List;

public interface IFavoritoService {
    List<Favorito> listar();
    void registrar(Favorito f);
    Favorito buscarPorId(Long id);
    void modificar(Favorito f);
    void eliminar(Long id);
}

