package pe.edu.smartspace.dtos;

import java.time.LocalDate;

public class ComentarioDTO {

    private Long idComentario;

    // ↓↓↓ SOLO IDS, NO ENTIDADES ↓↓↓
    private Long idDiseno;
    private Long idUsuario;

    // Datos útiles para mostrar en Angular
    private String username;
    private String nombreDiseno;

    private String textoComentario;
    private LocalDate fecha;

    // ===== GETTERS & SETTERS =====

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Long getIdDiseno() {
        return idDiseno;
    }

    public void setIdDiseno(Long idDiseno) {
        this.idDiseno = idDiseno;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreDiseno() {
        return nombreDiseno;
    }

    public void setNombreDiseno(String nombreDiseno) {
        this.nombreDiseno = nombreDiseno;
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
