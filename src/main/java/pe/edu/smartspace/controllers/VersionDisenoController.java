package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.VersionDisenoDTO;
import pe.edu.smartspace.entities.VersionDiseno;
import pe.edu.smartspace.servicesinterfaces.IVersionDisenoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/versiones")
public class VersionDisenoController {

    @Autowired
    private IVersionDisenoService service;

    @GetMapping
    public List<VersionDisenoDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, VersionDisenoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void registrar(@RequestBody VersionDisenoDTO dto) {
        ModelMapper m = new ModelMapper();
        VersionDiseno v = m.map(dto, VersionDiseno.class);
        service.registrar(v);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        VersionDiseno v = service.buscarPorId(id);
        if (v == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe una versión de diseño con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        VersionDisenoDTO dto = m.map(v, VersionDisenoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody VersionDisenoDTO dto) {
        ModelMapper m = new ModelMapper();
        VersionDiseno v = m.map(dto, VersionDiseno.class);

        VersionDiseno existente = service.buscarPorId(v.getIdVersion());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe una versión con el ID: " + v.getIdVersion());
        }

        service.modificar(v);
        return ResponseEntity.ok("Versión con ID " + v.getIdVersion() + " modificada correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        VersionDiseno v = service.buscarPorId(id);
        if (v == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una versión con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Versión con ID " + id + " eliminada correctamente.");
    }
}
