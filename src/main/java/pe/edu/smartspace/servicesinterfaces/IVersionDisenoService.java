package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.dtos.VersionDisenoDTO;
import pe.edu.smartspace.entities.VersionDiseno;

import java.util.List;

public interface IVersionDisenoService {
    List<VersionDiseno> listar();
    void registrar(VersionDiseno v);
    VersionDiseno buscarPorId(Long id);
    void modificar(VersionDiseno v);
    void eliminar(Long id);
}
