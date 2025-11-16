package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.NotificacionDTO;
import pe.edu.smartspace.entities.Notificacion;
import pe.edu.smartspace.servicesinterfaces.INotificacionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private INotificacionService service;

    @GetMapping
    public List<NotificacionDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, NotificacionDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void registrar(@RequestBody NotificacionDTO dto) {
        ModelMapper m = new ModelMapper();
        Notificacion n = m.map(dto, Notificacion.class);
        service.registrar(n);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Notificacion n = service.buscarPorId(id);
        if (n == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe una notificación con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        NotificacionDTO dto = m.map(n, NotificacionDTO.class);
        return ResponseEntity.ok(dto);
    }

    // Notificaciones por usuario
    @GetMapping("/usuario/{idUsuario}")
    public List<NotificacionDTO> listarPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        return service.listarPorUsuario(idUsuario).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, NotificacionDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody NotificacionDTO dto) {
        ModelMapper m = new ModelMapper();
        Notificacion n = m.map(dto, Notificacion.class);

        Notificacion existente = service.buscarPorId(n.getIdNotificacion());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe una notificación con el ID: " + n.getIdNotificacion());
        }

        service.modificar(n);
        return ResponseEntity.ok("Notificación con ID " + n.getIdNotificacion() + " modificada correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        Notificacion n = service.buscarPorId(id);
        if (n == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una notificación con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Notificación con ID " + id + " eliminada correctamente.");
    }
}
