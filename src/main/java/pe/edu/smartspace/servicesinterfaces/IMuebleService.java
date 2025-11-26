package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Mueble;
import java.util.List;

public interface IMuebleService {
    List<Mueble> listar();
    void registrar(Mueble m);
    Mueble buscarPorId(Long id);
    void modificar(Mueble m);
    void eliminar(Long id);
    List<Mueble> buscarPorCategoria(String categoria);
}
