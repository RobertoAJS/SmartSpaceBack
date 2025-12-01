package pe.edu.smartspace.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MuebleDTO {

    private Long idMueble;
    private String nombre;
    private String categoria;
    //dimension
    private Double alto;
    private Double ancho;
    private Double profundidad;
    //
    private String estilo;
    private Double precio;
    private Boolean sostenibilidad;
    private String programaDev;
    private String descripcion;

    // Para recibir el ID simple (ej: 1)
    private Long idUsuario;

    // Opcional: Para devolver el objeto completo al listar
    private UsuarioDTO usuario;


}
