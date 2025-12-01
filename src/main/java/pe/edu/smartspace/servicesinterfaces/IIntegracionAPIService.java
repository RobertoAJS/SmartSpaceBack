package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.IntegracionAPI;

import java.util.List;

public interface IIntegracionAPIService {
    List<IntegracionAPI> listar();
    void registrar(IntegracionAPI i);
    IntegracionAPI buscarPorId(Long id);
    void modificar(IntegracionAPI i);
    void eliminar(Long id);
}
