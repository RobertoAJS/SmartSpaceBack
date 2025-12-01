package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.repositories.IUsuarioRepository;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {


    @Autowired
    private IUsuarioRepository uR;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listar() {
        return uR.findAll();
    }

    @Override
    public void registrar(Usuario u) {

        // 1. Validar que el username no exista
        if (uR.buscarUsername(u.getUsername()) > 0) {
            throw new RuntimeException("El username ya existe: " + u.getUsername());
        }

        // 2. Habilitar usuario por defecto
        u.setStatusUsuario(true);

        // 3. Encriptar password con BCrypt
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        // 4. Guardar usuario
        Usuario usuarioGuardado = uR.save(u);

        // 5. Insertar rol por defecto en la tabla roles
        //    Aquí puedes usar "CLIENTE" y luego "ADMIN" para otros usuarios
        uR.insRol("CLIENTE", usuarioGuardado.getIdUsuario());
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
