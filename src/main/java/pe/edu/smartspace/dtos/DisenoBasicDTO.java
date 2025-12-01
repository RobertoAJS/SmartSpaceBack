package pe.edu.smartspace.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DisenoBasicDTO {
    private Long idDiseno;
    private String nombre;
    private String estado;
    private LocalDateTime fechaCreacion;
    // No ponemos 'urlModelo3d' aquí para no hacer pesada la lista inicial,
}
