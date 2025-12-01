package pe.edu.smartspace.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.smartspace.dtos.DisenoDTO;
import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.entities.Usuario;
import pe.edu.smartspace.servicesimplements.UploadService;
import pe.edu.smartspace.servicesinterfaces.IDisenoService;
import pe.edu.smartspace.servicesinterfaces.IMuebleService;
import pe.edu.smartspace.servicesinterfaces.IUsuarioService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disenos")
public class DisenoController {

    @Autowired
    private IDisenoService service;

    //Para cloud
    @Autowired
    private UploadService uploadService;

    @Autowired
    private IMuebleService muebleService;

    @Autowired
    private IUsuarioService usuarioService;
    //


    @GetMapping
    public List<DisenoDTO> listar() {
        return service.listar().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, DisenoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public void registrar(@RequestBody DisenoDTO dto) {
        ModelMapper m = new ModelMapper();
        Diseno d = m.map(dto, Diseno.class);
        service.registrar(d);
    }

    //Para subir datos con ahora archivo

    @PostMapping("/subir")
    public ResponseEntity<?> subirDiseno(
            @RequestParam("file") MultipartFile file,    // El archivo 3D
            @RequestParam("idMueble") Long idMueble,     // ID del mueble asociado
            @RequestParam("idUsuario") Long idUsuario,   // para relacionar con usuario
            @RequestParam("nombre") String nombre        // Nombre del diseño
    ) {
        try {
            // 1. Validar archivo
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            // 2. Subir a Cloudinary
            String urlModelo = uploadService.subirArchivo(file);

            // 3. Crear objeto Diseno
            Diseno d = new Diseno();
            d.setNombre(nombre);
            d.setUrlModelo3d(urlModelo); // Guardamos la URL

            // 4. Asociar con Mueble
            Mueble m = muebleService.buscarPorId(idMueble);
            if (m == null) {
                return ResponseEntity.badRequest().body("No existe mueble con ID: " + idMueble);
            }
            d.setMueble(m);

            // 5. Asociar Usuario
            Usuario u = usuarioService.buscarPorId(idUsuario);
            if (u == null) return ResponseEntity.badRequest().body("Usuario no existe");
            d.setUsuario(u);

            // 6. Guardar
            service.registrar(d);

            return ResponseEntity.ok("Diseño subido correctamente. URL: " + urlModelo);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el archivo: " + e.getMessage());
        }
    }

    //


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
