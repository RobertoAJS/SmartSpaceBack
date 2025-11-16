package pe.edu.smartspace.servicesinterfaces;

import pe.edu.smartspace.dtos.NotificacionDTO;
import pe.edu.smartspace.entities.Notificacion;

import java.util.List;

public interface INotificacionService {
    List<Notificacion> listar();
    void registrar(Notificacion n);
    Notificacion buscarPorId(Long id);
    void modificar(Notificacion n);
    void eliminar(Long id);
    List<Notificacion> listarPorUsuario(Long usuarioId);
}
