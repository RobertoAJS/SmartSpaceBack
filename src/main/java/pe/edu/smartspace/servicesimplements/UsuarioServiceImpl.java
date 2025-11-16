package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.repositories.IUsuarioRepository;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {


    @Autowired
    private IUsuarioRepository uR;

    @Override
    public List<Usuario> listar() {
        return uR.findAll();
    }

    @Override
    public void registrar(Usuario u) {
        uR.save(u);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return uR.findById(id).orElse(null);
    }

    @Override
    public void modificar(Usuario u) {
        uR.save(u);
    }

    @Override
    public void eliminar(Long id) {
        uR.deleteById(id);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return uR.findOneByUsername(username);
    }
}
