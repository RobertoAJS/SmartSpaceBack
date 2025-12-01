package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
@Getter
@Setter
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFavorito;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    // Relación con Mueble (producto favorito)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMueble", nullable = false)
    private Mueble mueble;

    @Column(name = "fecha_agregado", nullable = false)
    private LocalDateTime fechaAgregado;


    public Favorito() {
    }

    public Favorito(Long idFavorito, Usuario usuario, Mueble mueble, LocalDateTime fechaAgregado) {
        this.idFavorito = idFavorito;
        this.usuario = usuario;
        this.mueble = mueble;
        this.fechaAgregado = fechaAgregado;
    }

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
