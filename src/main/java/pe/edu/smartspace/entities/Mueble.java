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

    // 1. Relación con el Dueño (Cliente)
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;


    // 2. Relación con sus Diseños
    // CORRECCIÓN CLAVE: Usamos FetchType.EAGER para evitar el error LazyInitializationException
    @OneToMany(mappedBy = "mueble", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore // Evita bucles infinitos al serializar
    private List<Diseno> disenos = new ArrayList<>();
}
