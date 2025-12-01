package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.entities.Comentario;

import java.util.List;

public interface IComentarioService {
    List<Comentario> listar();
    List<Comentario> listarPorDiseno(Long idDiseno);
    void registrar(Comentario c);
    Comentario buscarPorId(Long id);
    void modificar(Comentario c);
    void eliminar(Long id);
}

