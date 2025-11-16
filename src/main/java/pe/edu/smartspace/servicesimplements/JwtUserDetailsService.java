package pe.edu.smartspace.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.repositories.IUsuarioRepository;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aquí tratamos "username" como correo
        Usuario user = repo.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        // Creamos la autoridad (rol por defecto)
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                roles
        );


    }
}
