package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.MuebleDTO;
import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.repositories.IMuebleRepository;
import pe.edu.smartspace.servicesinterfaces.IMuebleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/muebles")
public class MuebleController {

    @Autowired
    private IMuebleService service;

    @GetMapping
    public List<MuebleDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, MuebleDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void registrar(@RequestBody MuebleDTO dto) {
        ModelMapper m = new ModelMapper();
        Mueble mueble = m.map(dto, Mueble.class);
        service.registrar(mueble);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Mueble mueble = service.buscarPorId(id);
        if (mueble == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un mueble con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        MuebleDTO dto = m.map(mueble, MuebleDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody MuebleDTO dto) {
        ModelMapper m = new ModelMapper();
        Mueble mueble = m.map(dto, Mueble.class);

        Mueble existente = service.buscarPorId(mueble.getIdMueble());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un mueble con el ID: " + mueble.getIdMueble());
        }

        service.modificar(mueble);
        return ResponseEntity.ok("Mueble con ID " + mueble.getIdMueble() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        Mueble mueble = service.buscarPorId(id);
        if (mueble == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un mueble con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Mueble con ID " + id + " eliminado correctamente.");
    }

}

