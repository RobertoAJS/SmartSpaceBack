package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.smartspace.dtos.DisenoDTO;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.servicesinterfaces.IDisenoService;
import pe.edu.smartspace.services.FileStorageService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disenos")
@CrossOrigin(origins = "http://localhost:4200")

public class DisenoController {

    @Autowired
    private IDisenoService service;

    @Autowired
    private FileStorageService fileService;

    private final ModelMapper modelMapper = new ModelMapper();


    // ============================================================
    // 🔵 LISTAR TODOS
    // ============================================================
    @GetMapping
    public List<DisenoDTO> listar() {
        return service.listar()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // ============================================================
    // 🔵 SUBIR DISEÑO (con archivo)
    // ============================================================
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> registrarDiseño(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) {
        try {
            // 1️⃣ Guardar archivo físicamente
            String archivoNombre = fileService.save(file);

            // 2️⃣ Generar nombre automático: diseño001, diseño002...
            long count = service.contar();
            String nombreAuto = "diseño" + String.format("%03d", count + 1);

            // 3️⃣ Armar entidad
            Diseno d = new Diseno();
            d.setNombre(nombreAuto);
            d.setArchivoUrl(archivoNombre);
            d.setFechaCreacion(new Date());
            d.setEstado("Activo");

            Usuario u = new Usuario();
            u.setIdUsuario(userId);
            d.setUsuario(u);

            // 4️⃣ Guardar en BD
            service.registrar(d);

            return ResponseEntity.ok("Diseño subido correctamente.");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar diseño: " + e.getMessage());
        }
    }


    // ============================================================
    // 🔵 BUSCAR POR ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Long id) {
        Diseno d = service.buscarPorId(id);

        if (d == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un diseño con el ID: " + id);
        }

        return ResponseEntity.ok(convertToDTO(d));
    }


    // ============================================================
    // 🔵 MODIFICAR (sin archivo)
    // ============================================================
    @PutMapping
    public ResponseEntity<?> modificar(@RequestBody DisenoDTO dto) {

        Diseno existente = service.buscarPorId(dto.getIdDiseno());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un diseño con el ID: " + dto.getIdDiseno());
        }

        // Actualiza los campos (si deseas permitir cambiar archivo o usuario, se puede añadir)
        existente.setNombre(dto.getNombre());
        existente.setEstado(dto.getEstado());
        existente.setFechaCreacion(dto.getFechaCreacion());
        existente.setArchivoUrl(dto.getArchivoUrl());

        service.modificar(existente);

        return ResponseEntity.ok("Diseño actualizado correctamente.");
    }


    // ============================================================
    // 🔵 ELIMINAR
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        Diseno d = service.buscarPorId(id);

        if (d == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un diseño con ID: " + id);
        }

        service.eliminar(id);
        return ResponseEntity.ok("Diseño eliminado correctamente.");
    }


    // ============================================================
    // 🔄 MÉTODOS PRIVADOS PARA MAPEAR
    // ============================================================

    private DisenoDTO convertToDTO(Diseno d) {
        DisenoDTO dto = new DisenoDTO();
        dto.setIdDiseno(d.getIdDiseno());
        dto.setNombre(d.getNombre());
        dto.setFechaCreacion(d.getFechaCreacion());
        dto.setEstado(d.getEstado());
        dto.setArchivoUrl(d.getArchivoUrl());

        dto.setUserId(d.getUsuario().getIdUsuario());
        dto.setUsername(d.getUsuario().getUsername());

        return dto;
    }
}
