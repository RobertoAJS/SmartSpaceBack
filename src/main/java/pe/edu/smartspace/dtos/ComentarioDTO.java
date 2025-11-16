package pe.edu.smartspace.dtos;

import pe.edu.smartspace.entities.Diseno;
import pe.edu.smartspace.entities.Usuario;

import java.time.LocalDate;

public class ComentarioDTO {
    private Long idComentario;
    private Diseno diseno;
    private Usuario usuario;
    private String textoComentario;
    private LocalDate fecha;

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Diseno getDiseno() {
        return diseno;
    }

    public void setDiseno(Diseno diseno) {
        this.diseno = diseno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public void setTextoComentario(String textoComentario) {
        this.textoComentario = textoComentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
