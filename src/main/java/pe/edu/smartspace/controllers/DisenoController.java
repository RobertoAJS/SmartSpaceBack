package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.DisenoDTO;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.servicesinterfaces.IDisenoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disenos")
public class DisenoController {

    @Autowired
    private IDisenoService service;

    @GetMapping
    public List<DisenoDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DisenoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void registrar(@RequestBody DisenoDTO dto) {
        ModelMapper m = new ModelMapper();
        Diseno d = m.map(dto, Diseno.class);
        service.registrar(d);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Diseno d = service.buscarPorId(id);
        if (d == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un diseño con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        DisenoDTO dto = m.map(d, DisenoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody DisenoDTO dto) {
        ModelMapper m = new ModelMapper();
        Diseno d = m.map(dto, Diseno.class);

        Diseno existente = service.buscarPorId(d.getIdDiseno());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un diseño con el ID: " + d.getIdDiseno());
        }

        service.modificar(d);
        return ResponseEntity.ok("Diseño con ID " + d.getIdDiseno() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        Diseno d = service.buscarPorId(id);
        if (d == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un diseño con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Diseño con ID " + id + " eliminado correctamente.");
    }
}
