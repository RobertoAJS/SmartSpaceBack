package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.dtos.NotificacionDTO;
import pe.edu.smartspace.entities.Notificacion;
import pe.edu.smartspace.entities.Usuario;

import pe.edu.smartspace.repositories.INotificacionRepository;
import pe.edu.smartspace.repositories.IUsuarioRepository;
import pe.edu.smartspace.servicesinterfaces.INotificacionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements INotificacionService {

    @Autowired
    private INotificacionRepository nR;

    @Override
    public List<Notificacion> listar() {
        return nR.findAll();
    }

    @Override
    public void registrar(Notificacion n) {
        nR.save(n);
    }

    @Override
    public Notificacion buscarPorId(Long id) {
        return nR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Notificacion n) {
        nR.save(n);
    }

    @Override
    public void eliminar(Long id) {
        nR.deleteById(id);
    }

    @Override
    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        return nR.findByUsuario_IdUsuario(usuarioId);
    }
}
