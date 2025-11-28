package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "disenos")
@Getter @Setter
@NoArgsConstructor // para lombok, crea el constructor vacío
@AllArgsConstructor // para lombok, crea el constructor con todooo
public class Diseno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiseno;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "url_modelo_3d", length = 500, nullable = false)
    private String urlModelo3d; //  link de Cloudinary

    @Column(name = "estado", length = 20)
    private String estado; // Ej: "Publicado", "Borrador"

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;


    // Relación con usuario (dueño del diseño)
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    //Para relacionar con mueble (base del diseño)
    @ManyToOne
    @JoinColumn(name = "idMueble", nullable = false)
    private Mueble mueble;

    @PrePersist
    public void asignarFechaAutomaticamente() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "Activo"; // Valor por defecto
        }
    }


}
