package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Role;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.repositories.IRoleRepository;
import pe.edu.smartspace.repositories.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository repo;

    @Autowired
    private IRoleRepository roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = repo.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Obtener roles del usuario
        List<Role> rolesUsuario = roleRepo.findByUsuario_IdUsuario(user.getIdUsuario());

        List<GrantedAuthority> authorities = new ArrayList<>();

        rolesUsuario.forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getRol().toUpperCase()));
        });

        // Si no tiene roles asignados, poner CLIENTE por defecto
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isStatusUsuario(), // enabled
                true,
                true,
                true,
                authorities
        );
    }
}

