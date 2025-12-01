package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "disenos")
@Getter @Setter
public class Diseno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiseno;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "estado", length = 20)
    private String estado;

    // Nombre del archivo guardado
    @Column(name = "archivo_url", length = 200)
    private String archivoUrl;

    // Relación con usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;


    public Diseno() {
    }

    public Diseno(Long idDiseno, String nombre, Date fechaCreacion, String estado, Usuario usuario) {
        this.idDiseno = idDiseno;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.usuario = usuario;
        this.archivoUrl = archivoUrl;
    }

    public String getArchivoUrl() { return archivoUrl;  }

    public void setArchivoUrl(String archivoUrl) { this.archivoUrl = archivoUrl; }

    public Long getIdDiseno() {
        return idDiseno;
    }

    public void setIdDiseno(Long idDiseno) {
        this.idDiseno = idDiseno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
