package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.IntegracionAPIDTO;
import pe.edu.smartspace.entities.IntegracionAPI;
import pe.edu.smartspace.servicesinterfaces.IIntegracionAPIService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/integraciones")
public class IntegracionAPIController {

    @Autowired
    private IIntegracionAPIService service;

    @GetMapping
    public List<IntegracionAPIDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, IntegracionAPIDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public void registrar(@RequestBody IntegracionAPIDTO dto) {
        ModelMapper m = new ModelMapper();
        IntegracionAPI i = m.map(dto, IntegracionAPI.class);
        service.registrar(i);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        IntegracionAPI i = service.buscarPorId(id);
        if (i == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe una integración con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        IntegracionAPIDTO dto = m.map(i, IntegracionAPIDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody IntegracionAPIDTO dto) {
        ModelMapper m = new ModelMapper();
        IntegracionAPI i = m.map(dto, IntegracionAPI.class);

        IntegracionAPI existente = service.buscarPorId(i.getIdIntegracion());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe una integración con el ID: " + i.getIdIntegracion());
        }

        service.modificar(i);
        return ResponseEntity.ok("Integración con ID " + i.getIdIntegracion() + " modificada correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        IntegracionAPI i = service.buscarPorId(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una integración con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Integración con ID " + id + " eliminada correctamente.");
    }
}
