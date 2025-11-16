package pe.edu.smartspace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.securities.JwtTokenUtil;
import pe.edu.smartspace.dtos.JwtRequest;
import pe.edu.smartspace.dtos.JwtResponse;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest request) {
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Extraer el UserDetails del Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar token JWT
        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
