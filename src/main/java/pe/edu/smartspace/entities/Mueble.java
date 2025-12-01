package pe.edu.smartspace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "muebles")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMueble;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "categoria", length = 50)
    private String categoria;

    //dimensiones
    @Column(name = "alto", nullable = false)
    private Double alto;

    @Column(name = "ancho", nullable = false)
    private Double ancho;

    @Column(name = "profundidad", nullable = false)
    private Double profundidad;
    //

    @Column(name = "estilo", length = 50)
    private String estilo;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "sostenibilidad", nullable = false)
    private boolean sostenibilidad;

    @Column(name = "programa_Dev", length = 100)
    private String programaDev;

// RELACIONES

    // Relación con el Dueño (Cliente)

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación con sus Diseños (Un mueble puede tener varias versiones/diseños 3D)
    // Esto permite que al borrar el mueble, se borren sus diseños automáticamente (Cascade)
    @OneToMany(mappedBy = "mueble", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Evita bucles infinitos al pedir datos
    private List<Diseno> disenos = new ArrayList<>();
}
