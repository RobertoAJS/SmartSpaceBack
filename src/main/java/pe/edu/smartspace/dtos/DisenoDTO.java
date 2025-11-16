package pe.edu.smartspace.dtos;

import pe.edu.smartspace.entities.Usuario;

import java.util.Date;

public class DisenoDTO {

    private Long idDiseno;
    private String nombre;
    private Date fechaCreacion;
    private String estado;
    private Usuario usuario;


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
