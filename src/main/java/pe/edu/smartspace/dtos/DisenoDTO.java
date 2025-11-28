package pe.edu.smartspace.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class DisenoDTO {

    private Long idDiseno;
    private String nombre;
    private String urlModelo3d;
    private String estado;
    private LocalDateTime fechaCreacion;

    // Al devolver el diseño, también devolvemos la info básica del Usuario y Mueble
    private UsuarioDTO usuario;
    private MuebleDTO mueble;
}
