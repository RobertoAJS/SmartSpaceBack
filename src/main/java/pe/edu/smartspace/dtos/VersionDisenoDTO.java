package pe.edu.smartspace.dtos;

import pe.edu.smartspace.entities.Diseno;

import java.time.LocalDate;

public class VersionDisenoDTO {

    private Long idVersion;
    private Diseno diseno; // relación con Diseño
    private LocalDate fecha;
    private String archivoModelo;
    private String comentarios;

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
