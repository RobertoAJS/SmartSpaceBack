package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.PagoDTO;
import pe.edu.smartspace.entities.Pago;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.servicesinterfaces.IPagoService;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private IPagoService service;

    @GetMapping
    public List<PagoDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, PagoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void registrar(@RequestBody PagoDTO dto) {
        ModelMapper m = new ModelMapper();
        Pago p = m.map(dto, Pago.class);
        service.registrar(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Pago p = service.buscarPorId(id);
        if (p == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un pago con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        PagoDTO dto = m.map(p, PagoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody PagoDTO dto) {
        ModelMapper m = new ModelMapper();
        Pago p = m.map(dto, Pago.class);

        Pago existente = service.buscarPorId(p.getIdPago());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un pago con el ID: " + p.getIdPago());
        }

        service.modificar(p);
        return ResponseEntity.ok("Pago con ID " + p.getIdPago() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        Pago p = service.buscarPorId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un pago con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Pago con ID " + id + " eliminado correctamente.");
    }
}
