package pe.edu.smartspace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import pe.edu.smartspace.dtos.JwtRequest;
import pe.edu.smartspace.dtos.JwtResponse;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.securities.JwtTokenUtil;
import pe.edu.smartspace.servicesimplements.UsuarioServiceImpl;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest request) {

        // 1️⃣ Autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2️⃣ Obtener UserDetails
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3️⃣ Obtener usuario REAL desde la BD
        Usuario user = usuarioServiceImpl.buscarPorUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Long userId = user.getIdUsuario();

        // 4️⃣ Generar token incluyendo el userId
        String token = jwtTokenUtil.generateToken(userDetails, userId);

        // 5️⃣ Devolver token + userId
        return new JwtResponse(token, userId);
    }
}