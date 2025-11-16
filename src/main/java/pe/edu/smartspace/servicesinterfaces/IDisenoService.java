package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Diseno;
import java.util.List;

public interface IDisenoService {
    List<Diseno> listar();
    void registrar(Diseno d);
    Diseno buscarPorId(Long id);
    void modificar(Diseno d);
    void eliminar(Long id);
}
