package pe.edu.smartspace.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.FavoritoDTO;
import pe.edu.smartspace.entities.Favorito;
import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.securities.JwtTokenUtil;
import pe.edu.smartspace.servicesinterfaces.IFavoritoService;
import pe.edu.smartspace.servicesinterfaces.IMuebleService;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private IFavoritoService favoritoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IMuebleService muebleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // ============================================================
    // Obtener userId desde token
    // ============================================================
    private Long getUserIdFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        String token = header.substring(7);
        return jwtTokenUtil.getClaimFromToken(token, claims -> claims.get("userId", Long.class));
    }

    // ============================================================
    // LISTAR FAVORITOS (Admin: todos / Cliente: solo suyos)
    // ============================================================
    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest req, Authentication auth) {

        Long userId = getUserIdFromToken(req);

        boolean esAdmin = auth.getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Favorito> lista = esAdmin
                ? favoritoService.listar()
                : favoritoService.listarPorUsuario(userId);

        // 🚀 Mapeo manual (solución definitiva)
        List<FavoritoDTO> dtoLista = lista.stream().map(f -> {

            FavoritoDTO dto = new FavoritoDTO();
            dto.setIdFavorito(f.getIdFavorito());
            dto.setFechaAgregado(f.getFechaAgregado());

            // Evitar enviar el password del usuario
            Usuario u = new Usuario();
            u.setIdUsuario(f.getUsuario().getIdUsuario());
            u.setEmail(f.getUsuario().getEmail());
            u.setNombre(f.getUsuario().getNombre());
            u.setUsername(f.getUsuario().getUsername());
            dto.setUsuario(u);

            // Mueble completo 🔥🔥
            dto.setMueble(f.getMueble());

            return dto;

        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoLista);
    }

    // ============================================================
    // AGREGAR FAVORITO
    // ============================================================
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody FavoritoDTO dto, HttpServletRequest req) {

        Long userId = getUserIdFromToken(req);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o ausente.");
        }

        Usuario usuario = usuarioService.buscarPorId(userId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario no existe.");
        }

        if (dto.getMueble() == null || dto.getMueble().getIdMueble() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes enviar un idMueble válido.");
        }

        Mueble mueble = muebleService.buscarPorId(dto.getMueble().getIdMueble());
        if (mueble == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El mueble no existe.");
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setMueble(mueble);
        favorito.setFechaAgregado(LocalDateTime.now());

        favoritoService.registrar(favorito);

        return ResponseEntity.ok("Favorito agregado correctamente");
    }

    // ============================================================
    // ELIMINAR FAVORITO (Admin: cualquiera / Cliente: solo suyo)
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      HttpServletRequest req,
                                      Authentication auth) {

        Favorito fav = favoritoService.buscarPorId(id);

        if (fav == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El favorito no existe.");
        }

        Long userId = getUserIdFromToken(req);

        boolean esAdmin = auth.getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin && !fav.getUsuario().getIdUsuario().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes eliminar favoritos de otro usuario.");
        }

        favoritoService.eliminar(id);
        return ResponseEntity.ok("Favorito eliminado correctamente");
    }

}
