package pe.edu.smartspace.dtos;

import pe.edu.smartspace.entities.Mueble;
import pe.edu.smartspace.entities.Usuario;

import java.time.LocalDateTime;

public class FavoritoDTO {

    private Long idFavorito;
    private Usuario usuario;
    private Mueble mueble;
    private LocalDateTime fechaAgregado;

    public Long getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(Long idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mueble getMueble() {
        return mueble;
    }

    public void setMueble(Mueble mueble) {
        this.mueble = mueble;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
