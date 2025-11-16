package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.UsuarioDTO;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UsuarioDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void registrar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);
        service.registrar(u);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Usuario u = service.buscarPorId(id);
        if (u == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(u, UsuarioDTO.class);
        return ResponseEntity.ok(dto);
    }

    // Buscar por username
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorUsername(@RequestParam String username) {
        Usuario u = service.buscarPorUsername(username);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró usuario con username: " + username);
        }
        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(u, UsuarioDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);

        Usuario existente = service.buscarPorId(u.getIdUsuario());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un usuario con el ID: " + u.getIdUsuario());
        }

        service.modificar(u);
        return ResponseEntity.ok("Usuario con ID " + u.getIdUsuario() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        Usuario u = service.buscarPorId(id);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente.");
    }
}
