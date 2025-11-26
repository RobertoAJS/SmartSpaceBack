package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.FavoritoDTO;
import pe.edu.smartspace.entities.Favorito;
import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.servicesinterfaces.IFavoritoService;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;
import pe.edu.smartspace.servicesinterfaces.IMuebleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private IFavoritoService service;

    @GetMapping
    public List<FavoritoDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, FavoritoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public void registrar(@RequestBody FavoritoDTO dto) {
        ModelMapper m = new ModelMapper();
        Favorito f = m.map(dto, Favorito.class);
        service.registrar(f);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Favorito f = service.buscarPorId(id);
        if (f == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un favorito con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        FavoritoDTO dto = m.map(f, FavoritoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody FavoritoDTO dto) {
        ModelMapper m = new ModelMapper();
        Favorito f = m.map(dto, Favorito.class);

        Favorito existente = service.buscarPorId(f.getIdFavorito());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un favorito con el ID: " + f.getIdFavorito());
        }

        service.modificar(f);
        return ResponseEntity.ok("Favorito con ID " + f.getIdFavorito() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        Favorito f = service.buscarPorId(id);
        if (f == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un favorito con el ID: " + id);
        }
        service.eliminar(id);
        return ResponseEntity.ok("Favorito con ID " + id + " eliminado correctamente.");
    }

}
