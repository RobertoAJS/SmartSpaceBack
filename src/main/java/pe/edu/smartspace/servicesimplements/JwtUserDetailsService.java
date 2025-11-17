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
    private IRoleRepository roleRepo;  // <--- NUEVO

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aquí tratamos "username" como correo
        Usuario user = repo.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        // Traemos los roles desde la tabla ROLES
        List<Role> rolesUsuario = roleRepo.findByUsuario_IdUsuario(user.getIdUsuario());

        // Convertimos a GrantedAuthority (ADMIN, USER, etc)
        List<GrantedAuthority> authorities = new ArrayList<>();
        rolesUsuario.forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority(rol.getRol()));
        });

        // Si por alguna razón no tiene roles, al menos le damos USER
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("USER"));
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
