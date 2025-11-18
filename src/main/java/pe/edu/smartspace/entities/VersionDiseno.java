package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "versiones_disenos")
@Getter
@Setter
public class VersionDiseno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDiseno", nullable = false)
    private Diseno diseno;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "archivoModelo", length = 260)
    private String archivoModelo;

    @Column(name = "comentarios", length = 260)
    private String comentarios;


    public VersionDiseno() {
    }


    public VersionDiseno(Long idVersion, Diseno diseno, LocalDate fecha, String archivoModelo, String comentarios) {
        this.idVersion = idVersion;
        this.diseno = diseno;
        this.fecha = fecha;
        this.archivoModelo = archivoModelo;
        this.comentarios = comentarios;
    }

    public Long getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }

    public Diseno getDiseno() {
        return diseno;
    }

    public void setDiseno(Diseno diseno) {
        this.diseno = diseno;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getArchivoModelo() {
        return archivoModelo;
    }

    public void setArchivoModelo(String archivoModelo) {
        this.archivoModelo = archivoModelo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
