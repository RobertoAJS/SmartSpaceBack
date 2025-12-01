package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.smartspace.dtos.DisenoBasicDTO;
import pe.edu.smartspace.dtos.MuebleDTO;
import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.servicesinterfaces.IMuebleService;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/muebles")
public class MuebleController {

    @Autowired
    private IMuebleService service;

    // --- Necesitamos el servicio de usuarios para buscar al dueño ---
    @Autowired
    private IUsuarioService usuarioService;



    @GetMapping
    public List<MuebleDTO> listar() {
        return service.listar().stream().map(m -> {
            ModelMapper modelMapper = new ModelMapper();
            MuebleDTO dto = modelMapper.map(m, MuebleDTO.class);

            // Lógica manual para convertir la lista de entidades 'Diseno' a 'DisenoBasicDTO'
            // Verificamos que la lista no sea nula para evitar errores
            if (m.getDisenos() != null) {
                List<DisenoBasicDTO> listaDiseños = m.getDisenos().stream()
                        .map(d -> modelMapper.map(d, DisenoBasicDTO.class))
                        .collect(Collectors.toList());

                // Insertamos la lista convertida en el DTO del mueble
                dto.setDisenos(listaDiseños);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/buscar")
    public List<MuebleDTO> buscarPorCategoria(@RequestParam String categoria) {
        return service.buscarPorCategoria(categoria).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, MuebleDTO.class);
        }).collect(Collectors.toList());
    }


    // --- MÉTODO REGISTRAR MODIFICADO ---
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<?> registrar(@RequestBody MuebleDTO dto) {
        try {
            ModelMapper m = new ModelMapper();
            Mueble mueble = m.map(dto, Mueble.class);

            // 1. Validar que venga el ID del usuario
            if (dto.getIdUsuario() == null || dto.getIdUsuario() <= 0) {
                return ResponseEntity.badRequest().body("El ID del usuario es obligatorio para registrar un mueble.");
            }

            // 2. Buscar el usuario en la base de datos
            Usuario usuario = usuarioService.buscarPorId(dto.getIdUsuario());
            if (usuario == null) {
                return ResponseEntity.badRequest().body("No existe ningún usuario con el ID: " + dto.getIdUsuario());
            }

            // 3. Asignar el usuario encontrado al mueble
            // (Esto evita el error 'null value in column id_usuario')
            mueble.setUsuario(usuario);

            // 4. Guardar
            service.registrar(mueble);
            return ResponseEntity.ok("Mueble registrado exitosamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el mueble: " + e.getMessage());
        }
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


        // También cargamos los diseños al ver un solo mueble
        if (mueble.getDisenos() != null) {
            List<DisenoBasicDTO> listaDiseños = mueble.getDisenos().stream()
                    .map(d -> m.map(d, DisenoBasicDTO.class))
                    .collect(Collectors.toList());
            dto.setDisenos(listaDiseños);
        }

        return ResponseEntity.ok(dto);

    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody MuebleDTO dto) {
        ModelMapper m = new ModelMapper();
        Mueble mueble = m.map(dto, Mueble.class);

        // Nota: Al modificar, asegúrate de no perder el usuario.
        // Si el DTO no trae usuario, podrías necesitar buscar el mueble original y reasignarle su dueño.
        Mueble existente = service.buscarPorId(mueble.getIdMueble());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un mueble con el ID: " + mueble.getIdMueble());
        }

        // Mantenemos el usuario original si no se envió uno nuevo
        if (mueble.getUsuario() == null) {
            mueble.setUsuario(existente.getUsuario());
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

