package pe.edu.smartspace.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.ComentarioDTO;
import pe.edu.smartspace.entities.Comentario;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.securities.JwtTokenUtil;
import pe.edu.smartspace.servicesinterfaces.IComentarioService;
import pe.edu.smartspace.servicesinterfaces.IDisenoService;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private IComentarioService comentarioService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IDisenoService disenoService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // ============================
    // OBTENER USER ID DEL TOKEN
    // ============================
    private Long getUserIdFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;

        String token = header.substring(7);
        return jwtTokenUtil.getClaimFromToken(token,
                claims -> claims.get("userId", Long.class));
    }

    // ============================
    // MAPPER ENTIDAD → DTO PLANO
    // ============================
    private ComentarioDTO mapToDto(Comentario c) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setIdComentario(c.getIdComentario());
        dto.setTextoComentario(c.getTextoComentario());
        dto.setFecha(c.getFecha());

        if (c.getDiseno() != null) {
            dto.setIdDiseno(c.getDiseno().getIdDiseno());
            dto.setNombreDiseno(c.getDiseno().getNombre());
        }

        if (c.getUsuario() != null) {
            dto.setIdUsuario(c.getUsuario().getIdUsuario());
            dto.setUsername(c.getUsuario().getUsername());
        }

        return dto;
    }

    // ======================================
    // GET: COMENTARIOS DE UN DISEÑO
    // /api/comentarios?designId=15
    // ======================================
    @GetMapping
    public ResponseEntity<?> listarPorDiseno(
            @RequestParam(value = "designId", required = false) Long designId) {

        if (designId == null) {
            // Para que Angular no falle intentando parsear texto
            return ResponseEntity.ok(List.of());
        }

        List<Comentario> lista = comentarioService.listarPorDiseno(designId);

        List<ComentarioDTO> dtoLista = lista.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoLista);
    }

    // ======================================
    // GET: LISTAR TODOS LOS COMENTARIOS
    // /api/comentarios/all  (solo ADMIN)
    // ======================================
    @GetMapping("/all")
    public ResponseEntity<?> listarTodos(Authentication auth) {

        boolean esAdmin = auth != null && auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Solo ADMIN puede ver todos los comentarios.");
        }

        List<Comentario> lista = comentarioService.listar();

        List<ComentarioDTO> dtoLista = lista.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoLista);
    }

    // ======================================
    // GET: OBTENER COMENTARIO POR ID
    // /api/comentarios/{id}
    // ======================================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Comentario c = comentarioService.buscarPorId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Comentario no encontrado");
        }
        return ResponseEntity.ok(mapToDto(c));
    }

    // ======================================
    // POST: REGISTRAR COMENTARIO
    // BODY: { idDiseno, textoComentario }
    // ======================================
    @PostMapping
    public ResponseEntity<?> registrar(
            @RequestBody ComentarioDTO dto,
            HttpServletRequest req) {

        Long userId = getUserIdFromToken(req);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o ausente.");
        }

        if (dto.getIdDiseno() == null) {
            return ResponseEntity.badRequest().body("Debe enviar idDiseno.");
        }

        if (dto.getTextoComentario() == null || dto.getTextoComentario().isBlank()) {
            return ResponseEntity.badRequest().body("El comentario no puede estar vacío.");
        }

        Diseno diseno = disenoService.buscarPorId(dto.getIdDiseno());
        if (diseno == null) {
            return ResponseEntity.badRequest().body("El diseño no existe.");
        }

        Usuario usuario = usuarioService.buscarPorId(userId);

        Comentario c = new Comentario();
        c.setTextoComentario(dto.getTextoComentario());
        c.setFecha(LocalDate.now());
        c.setUsuario(usuario);
        c.setDiseno(diseno);

        comentarioService.registrar(c); // JPA setea el ID en el mismo objeto

        ComentarioDTO resp = mapToDto(c);
        return ResponseEntity.ok(resp);
    }

    // ======================================
    // PUT: MODIFICAR COMENTARIO (solo texto)
    // ======================================
    @PutMapping
    public ResponseEntity<?> modificar(
            @RequestBody ComentarioDTO dto,
            HttpServletRequest req,
            Authentication auth) {

        if (dto.getIdComentario() == null) {
            return ResponseEntity.badRequest().body("Debe enviar idComentario");
        }

        Comentario existente = comentarioService.buscarPorId(dto.getIdComentario());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El comentario no existe.");
        }

        Long userId = getUserIdFromToken(req);
        boolean esAdmin = auth != null && auth.getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin && (userId == null ||
                !existente.getUsuario().getIdUsuario().equals(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes editar comentarios de otros usuarios.");
        }

        if (dto.getTextoComentario() == null || dto.getTextoComentario().isBlank()) {
            return ResponseEntity.badRequest().body("El comentario no puede estar vacío.");
        }

        existente.setTextoComentario(dto.getTextoComentario());
        comentarioService.modificar(existente);

        return ResponseEntity.ok(mapToDto(existente));
    }

    // ======================================
    // DELETE: ELIMINAR COMENTARIO
    // ======================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long id,
            HttpServletRequest req,
            Authentication auth) {

        // Buscar comentario
        Comentario c = comentarioService.buscarPorId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "El comentario no existe."));
        }

        // Extraer ID del usuario autenticado
        Long userId = getUserIdFromToken(req);

        // Validar rol ADMIN
        boolean esAdmin = auth != null && auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Solo admin o dueño del comentario puede eliminar
        if (!esAdmin && (userId == null ||
                !c.getUsuario().getIdUsuario().equals(userId))) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "No puedes eliminar comentarios de otros usuarios."));
        }

        // Eliminar
        comentarioService.eliminar(id);

        // DEVOLVER JSON válido (Angular ya no fallará)
        return ResponseEntity.ok(Map.of("message", "Comentario eliminado"));
    }

}
