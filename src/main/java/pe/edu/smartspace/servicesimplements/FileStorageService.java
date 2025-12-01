package pe.edu.smartspace.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root = Paths.get("uploads");

    public FileStorageService() {
        try {
            // crear carpeta uploads si no existe
            Files.createDirectories(root);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la carpeta de uploads");
        }
    }

    public String save(MultipartFile file) {
        try {
            String nombre = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(nombre));
            return nombre;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo guardar el archivo");
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Archivo no legible");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar archivo");
        }
    }
}
